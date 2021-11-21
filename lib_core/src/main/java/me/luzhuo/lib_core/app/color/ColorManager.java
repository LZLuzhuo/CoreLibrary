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
package me.luzhuo.lib_core.app.color;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import me.luzhuo.lib_core.R;
import me.luzhuo.lib_core.app.base.CoreBaseApplication;

/**
 * Description: 色彩管理
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 15:20
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class ColorManager {
    private Context context;
    public ColorManager(Context context) {
        this.context = context;
    }
    public ColorManager() {
        this(CoreBaseApplication.appContext);
    }

    /**
     * <color name="colorAccent">#03DAC5</color>
     */
    public int getColorAccent() {
        return getAttrColor(R.attr.colorAccent);
    }

    /**
     * 30% 透明度的 ColorAccent
     */
    public int getSecondColorAccent() {
        return getSecondColor(getColorAccent());
    }

    /**
     * <color name="colorPrimary">#6200EE</color>
     */
    public int getColorPrimary() {
        return getAttrColor(R.attr.colorPrimary);
    }

    /**
     * <color name="colorPrimaryDark">#3700B3</color>
     */
    public int getColorPrimaryDark() {
        return getAttrColor(R.attr.colorPrimaryDark);
    }

    /**
     * TextView 的默认颜色 是三级文本色
     */
    public int getTextColorDefault() {
        return getTextColorTertiary();
    }

    /**
     * 文本一级颜色
     * ?android:attr/textColorPrimary
     */
    public int getTextColorPrimary() {
        return getAndroidAttrColor(android.R.attr.textColorPrimary);
    }

    /**
     * 文本二级颜色
     * ?android:attr/textColorSecondary
     */
    public int getTextColorSecondary() {
        return getAndroidAttrColor(android.R.attr.textColorSecondary);
    }

    /**
     * 文本三级颜色
     * ?android:attr/textColorTertiary
     */
    public int getTextColorTertiary() {
        return getAndroidAttrColor(android.R.attr.textColorTertiary);
    }

    /**
     * 背景的前景色
     */
    public int getColorForeground() {
        return getColor(R.color.core_colorForeground);
    }

    /**
     * 获取颜色值
     */
    public int getColor(@ColorRes int colorId) {
        return context.getResources().getColor(colorId);
    }

    /**
     * 30% 透明度的 Color
     */
    public int getSecondColor(int color) {
        return (0x4D << 24) | (color & 0x00FFFFFF);
    }

    /**
     * 获取 ?attr 的颜色值
     */
    public int getAttrColor(@ColorInt int colorAttrId) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(colorAttrId, typedValue, true);
        return typedValue.data;
    }

    /**
     * 获取 ?android:attr 的颜色值
     */
    public int getAndroidAttrColor(@ColorInt int androidColorAttrId) {
        TypedArray array = context.getTheme().obtainStyledAttributes(new int[]{ androidColorAttrId });
        int color = array.getColor(0, 0xDD000000);
        array.recycle();
        return color;
    }
}
