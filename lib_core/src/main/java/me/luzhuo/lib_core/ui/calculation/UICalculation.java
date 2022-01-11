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
package me.luzhuo.lib_core.ui.calculation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description: UI计算
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/27 23:19
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class UICalculation {
    private Context context;
    private StatusBarUtils statusBar = new StatusBarUtils();
    private NavigationBarUtils navigationBar = new NavigationBarUtils();

    public UICalculation(@NonNull Context context){
        this.context = context.getApplicationContext();
    }

    /**
     * dp to px
     * @param dp
     * @return px
     */
    public int dp2px(float dp){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * px to dp
     * @param px
     * @return dp
     */
    public int px2dp(float px) {
        final float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    /**
     * 获取屏幕宽高
     * get width and height form phone display
     * @return wh [width, height]
     */
    public int[] getDisplay() {
        final Resources res = context.getResources();
        int width = res.getDisplayMetrics().widthPixels;
        int height = res.getDisplayMetrics().heightPixels;
        return new int[]{width, height};
    }

    /**
     * 获取根据屏幕缩小指定比例的 LayoutParams
     * get LayoutParams from scaled by display
     *
     * Example:
     * <pre>
     * Button.setLayoutParams(DisplayMatchUtils.getParamsScale(0.5f,null));
     * </pre>
     *
     * @param widthScale 0.5f(50％) or null(MATCH_PARENT), [0f,1f]
     * @param heightScale 0.5f(50％) or null(MATCH_PARENT), [0f,1f]
     * @return
     */
    public LinearLayout.LayoutParams getParamsScale(Float widthScale, Float heightScale) {
        int[] wh = getDisplay();
        int width = wh[0];
        int height = wh[1];

        int widthParams = LinearLayout.LayoutParams.MATCH_PARENT;
        int heightParams = LinearLayout.LayoutParams.MATCH_PARENT;

        if(widthScale != null) widthParams = (int) (width * widthScale + 0.5f);
        if(heightScale != null) heightParams = (int) (height * heightScale + 0.5f);

        return new LinearLayout.LayoutParams(widthParams, heightParams);
    }

    /**
     * 从 textview 获取字体高度
     * get font height by textview
     * @param textView
     * @return
     */
    public int getFontHeight(TextView textView) {
        if (textView == null) return -1;

        Paint paint = new Paint();
        paint.setTextSize(textView.getTextSize());
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.bottom - fm.top);
    }

    /**
     * 获取 StatusBar 高度, 并且要求沉浸式状态栏时对 StatusBar 的值无影响
     * @param view 任意View
     * @return 必然会返回一个 StatusBar 的高度, 不能保证高度值准确, 但尽量准确
     */
    public int getStatusBarHeight(@Nullable View view) {
        return statusBar.getStatusBarHeight(context, view);
    }

    /**
     * 获取 NavigationBar 高度, 并且要求沉浸式状态栏时对 NavigationBar 的值无影响
     * 从 android.R.dimen.navigation_bar_height 中获取 status bar 高度
     * @return 有则返回具体值, 没有则返回-1
     */
    public int getNavigationBarHeight() {
        return navigationBar.getNavigationBarHeight(context);
    }

    /**
     * 判断现在是否有NavigationBar
     * @return 有返回true, 没有返回false
     */
    public boolean hasNavigationBar() {
        return navigationBar.hasNavigationBar(context);
    }

    /**
     * 获取当前NavigationBar的高度
     * 如果有则返回NavigationBar的高度, 没有则返回0
     * @return NavigationBar的高度, 或者0
     */
    public int getCurrentNavigationBar() {
        return navigationBar.getCurrentNavigationBar(context);
    }
}
