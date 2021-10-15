/* Copyright 2021 Luzhuo. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.luzhuo.lib_core.app.email;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.FileProvider;
import me.luzhuo.lib_core.app.email.bean.EmailContent;
import me.luzhuo.lib_core.app.email.bean.EmailUser;
import me.luzhuo.lib_core.ui.toast.ToastManager;

import static me.luzhuo.lib_core.app.appinfo.AppManager.AUTHORITY;

/**
 * Description: Email管理
 * @Author: Luzhuo
 * @Creation Date: 2021/10/15 23:31
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class EmailManager {

    /**
     * 发送邮件
     * 1. 如果没有附件, 则通过发送邮件的方式方法
     * 2. 如果有附件, 则通过分享的方式发送, 需要用户主动选择邮件作为发送目标
     */
    public void sendEmail(Context context, EmailUser user, EmailContent content) {
        if (content.getEmailAttachment().size() <= 0) {
            sendEmailNoFile(context, user, content);
        } else {
            // sendEmailShareFile(context, user, content);
            sendEmailHaveFile(context, user, content);
        }
    }

    /**
     * 发送带有附件的邮件, 不会弹出选择框
     * 通过过滤 Intent.ACTION_SENDTO 的 Intent, 去构建一个新的 createChooser 的方式实现调到写邮件界面
     */
    protected void sendEmailHaveFile(Context context, EmailUser user, EmailContent content) {
        try {
            /*
            所有邮件信息的Intent
            选择框会弹出非常多的备选应用, 包括我们不需要的
             */
            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_EMAIL, user.getShouJianRen());
            intent.putExtra(Intent.EXTRA_CC, user.getChaoSongZhe());
            intent.putExtra(Intent.EXTRA_BCC, user.getMiSongZhe());
            intent.putExtra(Intent.EXTRA_SUBJECT, content.getEmailTitle());
            intent.putExtra(Intent.EXTRA_TEXT, content.getEmailContent());

            // files
            ArrayList<Uri> imageUris = new ArrayList<>();
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) { // <= android6.0
                for (File file : content.getEmailAttachment()) { imageUris.add(Uri.fromFile(file)); }
            } else { // >= android7.1
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                for (File file : content.getEmailAttachment()) { imageUris.add(FileProvider.getUriForFile(context, AUTHORITY + context.getPackageName(), file)); }
            }
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);

            /*
            核心部分:
            创建一个 ACTION_SENDTO 和 "mailto:" 组合的Intent
            并使用该组合去找到支持邮件发送的应用的 ResolveInfo 的集合
            ResolveInfo 是解析 manifest 文件中的 Intent 过滤器所得到的信息
             */
            Intent queryIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
            List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(queryIntent, PackageManager.MATCH_DEFAULT_ONLY | PackageManager.GET_RESOLVED_FILTER);

            // 构建我们的目标Intent
            ArrayList<Intent> targetIntents = new ArrayList<>();
            for (ResolveInfo info : resolveInfos) {
                ActivityInfo ai = info.activityInfo;
                Intent filterIntent = new Intent(intent);
                filterIntent.setPackage(ai.packageName);
                filterIntent.setComponent(new ComponentName(ai.packageName, ai.name));
                targetIntents.add(filterIntent);
            }

            /*
            构建createChooser
            再使用 Intent.EXTRA_INITIAL_INTENTS 传入目标时, 第一个Intent会多出一个, 所以需要remove(0)
             */
            Intent chooser = Intent.createChooser(targetIntents.remove(0), content.getEmailTitle());
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetIntents.toArray(new Parcelable[]{}));
            context.startActivity(chooser);
        } catch (Exception e) {
            e.printStackTrace();
            ToastManager.show(context, "请安装邮箱应用!");
        }
    }

    /**
     * 会弹出一个选择框让用户进行选择, 用户的选择对象除了Email之外, 还有其他的选项
     */
    @Deprecated
    protected void sendEmailShareFile(Context context, EmailUser user, EmailContent content) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            intent.setData(Uri.parse("mailto:"));
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, user.getShouJianRen());
            intent.putExtra(Intent.EXTRA_CC, user.getChaoSongZhe());
            intent.putExtra(Intent.EXTRA_BCC, user.getMiSongZhe());
            intent.putExtra(Intent.EXTRA_SUBJECT, content.getEmailTitle());
            intent.putExtra(Intent.EXTRA_TEXT, content.getEmailContent());

            ArrayList<Uri> imageUris = new ArrayList<>();
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) { // <= android6.0
                for (File file : content.getEmailAttachment()) { imageUris.add(Uri.fromFile(file)); }
            } else { // >= android7.1
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                for (File file : content.getEmailAttachment()) { imageUris.add(FileProvider.getUriForFile(context, AUTHORITY + context.getPackageName(), file)); }
            }
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);

            context.startActivity(Intent.createChooser(intent, "请选择电子邮箱"));
        } catch (Exception e) {
            e.printStackTrace();
            ToastManager.show(context, "请安装邮箱应用!");
        }
    }

    /**
     * 没有附件, 直接跳到写邮件页面
     */
    protected void sendEmailNoFile(Context context, EmailUser user, EmailContent content) {
        try {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, user.getShouJianRen());
            intent.putExtra(Intent.EXTRA_CC, user.getChaoSongZhe());
            intent.putExtra(Intent.EXTRA_BCC, user.getMiSongZhe());
            intent.putExtra(Intent.EXTRA_SUBJECT, content.getEmailTitle());
            intent.putExtra(Intent.EXTRA_TEXT, content.getEmailContent());

            context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            ToastManager.show(context, "请安装邮箱应用!");
        }
    }
}
