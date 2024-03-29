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

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.File;
import java.security.MessageDigest;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import me.luzhuo.lib_core.app.appinfo.bean.AppInfo;
import me.luzhuo.lib_core.app.appinfo.callback.AppForegroundCallback;
import me.luzhuo.lib_core.app.base.CoreBaseApplication;
import me.luzhuo.lib_core.ui.toast.ToastManager;

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
    public final static String AUTHORITY = "me.luzhuo.fileprovider.";
    private Context context;

    public AppManager() {
        this.context = CoreBaseApplication.appContext;
    }

    public AppManager(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 检查当前应用的版本是否为 Debug 版本
     * @return 如果是Debug版本则返回true, 否则返回false
     */
    public boolean isDebug() {
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
    @Nullable
    public AppInfo getAppInfo() {
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
    @Nullable
    public AppInfo getAppInfo(@NonNull String apkPath) {
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
     * @return boolean true:是, false:不是
     */
    public boolean isDarkTheme() {
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
    public void setDarkMode(@NonNull DarkMode mode) {
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
    @Nullable
    public String getSign() {
        try {
            final String pkgName = context.getPackageName();
            PackageInfo pis = context.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_SIGNATURES);
            return hexdigest(pis.signatures[0].toByteArray());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取设备唯一ID
     * 根据硬件信息生成, 不需要权限, 不随App卸载和设备恢复出厂设置而改变
     * @return 5F200783B117251F137FA19A365EFD932293E8D1
     */
    @Nullable
    public String getDeviceId() {
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
    public void onBackPressed(@NonNull FragmentActivity activity) {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(activity.getBaseContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            activity.finish();
        }
    }

    /**
     * 安装应用
     * @param context Context
     * @param appFilePath app file path
     */
    @SuppressLint("InlinedApi")
    @RequiresPermission(Manifest.permission.REQUEST_INSTALL_PACKAGES)
    public void installApk(@Nullable Context context, @Nullable File appFilePath) {
        if (context == null || appFilePath == null || !appFilePath.exists()) return;

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) { // <= android6.0
            installApk_(context, appFilePath);
        } else { // >= android7.1
            installApkN(context, appFilePath);
        }
    }

    /**
     * 安装应用
     * android <= android6.0
     */
    private void installApk_(Context context, File appFilePath) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setDataAndType(Uri.parse("file://" + appFilePath.getAbsolutePath()), "application/vnd.android.package-archive");
        context.startActivity(install);
    }

    /**
     * 安装应用, 通过 FileProvider 共享目录的方式
     * android == android 7.0
     */
    private void installApkN(Context context, File appFilePath) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // 对目标应用临时授权该 Uri 所代表的文件
        install.setDataAndType(FileProvider.getUriForFile(context, AUTHORITY + context.getPackageName(), appFilePath), "application/vnd.android.package-archive");
        context.startActivity(install);
    }

    /**
     * 跳转到应用市场
     */
    public void startAppStore(@NonNull Context context) {
        try {
            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            ToastManager.show(context, "未安装应用市场");
        }
    }

    /**
     * App前后台切换的监听
     * 请在Application中调用
     * @param callback 前后切换的回调接口
     */
    public void registerAppForegroundCallback(@Nullable AppForegroundCallback callback) {
        AppForeground.getInstance().registerAppForegroundCallback((Application) CoreBaseApplication.appContext, callback);
    }

    /**
     * 将Activity界面设置成灰色
     * 每个Activity都要设置, 可以设置在 setContentView() 之前
     */
    public void activityGray(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (window == null) return;

        View view = window.getDecorView();
        viewGray(view);
    }

    /**
     * 将指定的View设置为灰色
     */
    public void viewGray(@NonNull View view) {
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        // 设置饱和度
        cm.setSaturation(0f);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        view.setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }

    /**
     * 在多进程的App中, 常常需要知道当前进程是 主进程 还是 后台进程.
     * @param applicationId BuildConfig.APPLICATION_ID
     * @return 主进程true, 非主进程false
     */
    public boolean isMainProcess(@NonNull String applicationId) {
        return applicationId.equals(currentProcessName());
    }

    /**
     * 获取当前进程名
     */
    @Nullable
    public String currentProcessName() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) return getCurrentProcessName();
        else return getCurrentProcessName(context);
    }

    /**
     * 获取当前进程名
     *
     * 此函数的弊端:
     * 1. ActivityManager.getRunningAppProcesses() 需要跨进程 和 系统进程的ActivityManagerService 通信, 效率不高
     * 2. 拿到 RunningAppProcessInfo 的列表之后, 需要遍历一遍找到与当前进程的信息, 循环会影响效率
     * 3. 此函数的多次地方会调用失败, 返回null
     */
    @Nullable
    private String getCurrentProcessName(@Nullable Context context) {
        if (context == null) return null;

        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return null;

        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
        if (runningAppProcesses == null) return null;

        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
            if (runningAppProcess.pid == myPid) return runningAppProcess.processName;
        }
        return null;
    }

    /**
     * 获取当前进程名
     */
    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.P)
    private String getCurrentProcessName() {
        return Application.getProcessName();
    }
}
