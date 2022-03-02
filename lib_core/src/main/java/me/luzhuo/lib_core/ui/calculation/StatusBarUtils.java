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

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class StatusBarUtils {

    /**
     * 获取 StatusBar 高度, 并且要求沉浸式状态栏时对 StatusBar 的值无影响
     * @param view 任意View
     * @return 必然会返回一个 StatusBar 的高度, 不能保证高度值准确, 但尽量准确
     */
    public int getStatusBarHeight(@NonNull Context context, @Nullable View view) {
        int statusBarHeight;
        statusBarHeight = getStatusBarHeightByDimen(context);
        if (statusBarHeight <= 0) getStatusBarHeightByView(view);
        if (statusBarHeight <= 0) getStatusBarHeightByDefault(context);
        return statusBarHeight;
    }

    /**
     * 从 android.R.dimen.status_bar_height 中获取 status bar 高度
     */
    protected int getStatusBarHeightByDimen(@NonNull Context context) {
        int statusBarHeight = -1;
        // 从系统未公开的 android.R.dimen.status_bar_height 中获取状态栏的高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);

        return statusBarHeight;
    }

    /**
     * 获取不到时, 给的默认的24dp
     * @return
     */
    protected int getStatusBarHeightByDefault(@NonNull Context context) {
        return new UICalculation(context).dp2px(24f);
    }

    /**
     * 获取 statusbar 高度
     * get statusbar hieght
     * @param v 任意View
     * @return
     */
    protected int getStatusBarHeightByView(@Nullable View v) {
        if (v == null) return -1;

        Rect frame = new Rect();
        v.getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }
}
