/* Copyright 2020 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_core.app.appinfo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import java.security.MessageDigest;

import me.luzhuo.lib_core.app.appinfo.bean.AppInfo;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;
import static androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode;

/**
 * Description: 应用管理
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/9/14 17:34
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class AppManager {

    /**
     * 检查当前应用的版本是否为 Debug 版本
     * @return 如果是Debug版本则返回true, 否则返回false
     */
    public boolean isDebug(Context context){
        try {
            ApplicationInfo info = context.getApplicationInfo();
            /*
             Comes from android:debuggable of the <application> tag.
            */
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取该应用的版本信息
     * @return AppInfo
     */
    public AppInfo getAppInfo(Context context){
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);

            AppInfo info = new AppInfo();
            info.versionName = packageInfo.versionName;
            info.versionCode = packageInfo.versionCode;
            info.packageName = packageInfo.packageName;
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 指定apk路径 的应用版本信息
     * @param apkPath apk文件路径
     * @return AppInfo
     */
    public AppInfo getAppInfo(Context context, String apkPath) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);

            AppInfo info = new AppInfo();
            info.versionName = packageInfo.versionName;
            info.versionCode = packageInfo.versionCode;
            info.packageName = packageInfo.packageName;
            return info;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 是否是深色主题
     * @param context Context
     * @return boolean true:是, false:不是
     */
    public boolean isDarkTheme(Context context) {
        int flag = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return flag == Configuration.UI_MODE_NIGHT_YES;
    }

    public enum DarkMode {
        Day/*白天模式*/, Night/*夜晚模式*/, System/*跟随系统*/
    }

    /**
     * 设置黑暗模式
     * 1. 设置夜间模式后, 应用结束也会失效
     * 2. 成功切换模式后, Activity会销毁重建
     */
    public void setDarkMode(DarkMode mode) {
        switch (mode) {
            case Day:
                setDefaultNightMode(MODE_NIGHT_NO);
                break;
            case Night:
                setDefaultNightMode(MODE_NIGHT_YES);
                break;
            case System:
                setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    /**
     * 获取该应用的签名 (MD5)
     * @return app sign; b032c61a30fd981a18aa6278c34b1e2a
     */
    public String getSign(Context context) {
        try {
            final String pkgName = context.getPackageName();
            PackageInfo pis = context.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
            return hexdigest(pis.signatures[0].toByteArray());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取设备唯一ID
     * 根据硬件信息生成, 不需要权限, 不随App卸载和设备恢复出厂设置而改变
     * @return 5F200783B117251F137FA19A365EFD932293E8D1
     */
    public String getDeviceId(Context context) {
        return new DeviceIdUtils().getDeviceId(context);
    }

    /**
     * 获取设别名: 手机品牌_手机型号
     * @return HUAWEI_EDI-AL10
     */
    public String getDeviceName() {
        return Build.BRAND.concat("_").concat(Build.MODEL);
    }

    /**
     * 将签名字符转换成32位MD5签名
     */
    private String hexdigest(byte[] paramArrayOfByte) {
        final char[] hexDigits = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
        try {
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            localMessageDigest.update(paramArrayOfByte);
            byte[] arrayOfByte = localMessageDigest.digest();
            char[] arrayOfChar = new char[32];
            for (int i = 0, j = 0;; i++, j++) {
                if (i >= 16) {
                    return new String(arrayOfChar);
                }
                int k = arrayOfByte[i];
                arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
                arrayOfChar[++j] = hexDigits[(k & 0xF)];
            }
        } catch (Exception ignored) { }
        return "";
    }

    /**
     * 对指定的View进行截屏
     * @param view 指定的View
     * @return 截频返回的Bitmap
     */
    public Bitmap screenshot(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    /**
     * 获得当前进程的名字
     * @return 进程名, 默认进程为报名 (com.jincai.myapplication)
     */
    public String processName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) { return appProcess.processName; }
        }
        return "";
    }

    /**
     * 退出应用程序
     */
    public void exit() {
        try {
            android.os.Process.killProcess(android.os.Process.myPid());
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            Runtime.getRuntime().exit(-1);
        }
    }

    private long firstTime;
    /**
     * 再按一次退出应用
     */
    public void onBackPressed(Activity activity) {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(activity.getBaseContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            activity.finish();
        }
    }
}
