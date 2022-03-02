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
package me.luzhuo.lib_core.ui.calculation;

import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import me.luzhuo.lib_core.app.phone.PhoneManager;
import me.luzhuo.lib_core.app.phone.enums.Rom;

/**
 * Description: NavigationBar工具类
 * @Author: Luzhuo
 * @Creation Date: 2021/12/11 17:15
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
class NavigationBarUtils {

    /**
     * 获取 NavigationBar 高度, 并且要求沉浸式状态栏时对 NavigationBar 的值无影响
     * 从 android.R.dimen.navigation_bar_height 中获取 status bar 高度
     * 126px
     * @return 有则返回具体值, 没有则返回-1
     */
    public int getNavigationBarHeight(@NonNull Context context) {
        int statusBarHeight = -1;
        // 从系统未公开的 android.R.dimen.navigation_bar_height 中获取状态栏的高度
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);

        return statusBarHeight;
    }

    /**
     * 判断现在是否有NavigationBar
     * @return 有返回true, 没有返回false
     */
    public boolean hasNavigationBar(@NonNull Context context) {
        if (getNavigationBarHeight(context) <= 0) return false; // 没有则必然没有, 有则未必有
        Rom rom = new PhoneManager().getPhoneRom();
        if (rom == Rom.Rom_Huawei && isHuaWeiHideNav(context)) return false;
        if (rom == Rom.Rom_Miui && isMiuiFullScreen(context)) return false;
        if (rom == Rom.Rom_Vivo && isVivoFullScreen(context)) return false;
        return isHasNavigationBar(context);
    }

    /**
     * 获取当前NavigationBar的高度
     * 如果有则返回NavigationBar的高度, 没有则返回0
     * @return NavigationBar的高度, 或者0
     */
    public int getCurrentNavigationBar(@NonNull Context context) {
        if (hasNavigationBar(context)) {
            return getNavigationBarHeight(context);
        } else return 0;
    }

    /**
     * 华为手机是否隐藏了虚拟导航栏
     * @return true隐藏了, false未隐藏
     */
    private boolean isHuaWeiHideNav(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return Settings.System.getInt(context.getContentResolver(), "navigationbar_is_min", 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(), "navigationbar_is_min", 0) != 0;
        }
    }

    /**
     * 小米手机是否开启手势操作
     * @return false 表示使用的是NavigationBar, true表示使用的是手势, 默认false
     */
    private boolean isMiuiFullScreen(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
        } return false;
    }

    /**
     * Vivo手机是否开启手势操作
     * @return false 表示使用的是NavigationBar, true表示使用的是手势, 默认flase
     */
    private boolean isVivoFullScreen(@NonNull Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "navigation_gesture_on", 0) != 0;
    }

    /**
     * 其他手机根据屏幕真实高度与显示高度是否相同来判断
     */
    private boolean isHasNavigationBar(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        // 真实的宽高
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(realDisplayMetrics);
        }
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;

        // 显示的宽高
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        // 部分无良厂商的手势操作，显示高度 + 导航栏高度，竟然大于物理高度，对于这种情况，直接默认未启用导航栏
        if (displayHeight + getNavigationBarHeight(context) > realHeight) return false;

        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }
}
