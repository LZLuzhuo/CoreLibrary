/* Copyright 2016 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_core.app.sms;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import me.luzhuo.lib_core.ui.toast.ToastManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 短信
 * @Author: Luzhuo
 * @Creation Date: 2016/3/14 15:15
 * @Copyright: Copyright 2016 Luzhuo. All rights reserved.
 **/
public class SMSManager {
    protected static final String SMSACTION = "android.provider.Telephony.SMS_RECEIVED";

    /**
     * 短信验证码(数字)精确匹配
     *
     * <p>
     * Example:
     * final String msgConent = "【XX纺织网】您的校验码：811257，您正在注册成为会员，感谢您的支持！(来自10655475603892696867)";
     * phoneManager.matchMsg(msgConent, "XX纺织网", 6)
     * </p>
     *
     * @param smsContent 短信的文本内容
     * @param contains 必须包含该内容 // "aaa" 被匹配的短信内容
     * @param continuous 验证码的位数 // 5 连续的位数 （连续5位）
     * @return 成功匹配出来的字符串 / ""
     */
    public String match(String smsContent, String contains, int continuous){
        final String regex = "[\\d]{" + continuous + ",}"; // 匹配正则 (连续5个的数字)
        if (smsContent.contains(contains)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(smsContent);
            while (matcher.find()) {
                String MatcherText = matcher.group();
                if (MatcherText.length() == continuous) return MatcherText;
            }
        }
        return "";
    }

    /**
     * 监听短信信息
     * 需要权限 android.permission.RECEIVE_SMS
     */
    public void setSMSListener(final FragmentActivity activity, final SMSReceiveCallback callback){
        if (activity == null) return;
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ToastManager.show(activity, "请授予短信接收权限!");
            return;
        }

        // 广播接收者
        final BroadcastReceiver messageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                if(extras == null) return;

                Object[] objs = (Object[]) extras.get("pdus");
                if(objs == null) return;

                for (Object obj : objs) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
                    String msgContent = smsMessage.getMessageBody();

                    if (callback != null) callback.onReceive(msgContent);
                }
            }
        };

        // 动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(SMSACTION);
        activity.registerReceiver(messageReceiver, filter);

        // 注销广播
        activity.getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            public void destroy(){
                activity.unregisterReceiver(messageReceiver);
            }
        });
    }

    /**
     * 发送短信 (不会写入数据库, 且用户无感知)
     * 需要权限 android.permission.SEND_SMS
     * 多卡状态下, 经测试, 发送短信的号码与手机选择的移动数据的卡有关
     * @param phone 手机号码
     * @param content 短信内容
     */
    public void send(Context context, String phone, String content) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ToastManager.show(context, "请授予短信发送权限!");
            return;
        }

        SmsManager smsManager = SmsManager.getDefault();
        // 分割短信内容, 每条短信最多支持70个字符
        ArrayList<String> divideMessage = smsManager.divideMessage(content);
        for (String singleContent : divideMessage) {
            smsManager.sendTextMessage(phone, null, singleContent, null, null);
        }
    }

    /**
     * 跳转到短信发送界面
     * @param phone 短信接收者的号码, 必填
     * @param smsContent 短信内容, 可不填
     */
    public void send2Box(Context context, String phone, String smsContent) {
        if (TextUtils.isEmpty(phone)) return;

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + phone));
        intent.putExtra("sms_body", smsContent);
        context.startActivity(intent);
    }
}
