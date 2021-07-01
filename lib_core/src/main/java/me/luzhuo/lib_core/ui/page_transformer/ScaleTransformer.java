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
package me.luzhuo.lib_core.ui.page_transformer;

import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

/**
 * Description: Viewpager2 的缩放页面切换
 * @Author: Luzhuo
 * @Creation Date: 2021/4/14 0:25
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class ScaleTransformer implements ViewPager2.PageTransformer {
    protected static final float DEFAULT_MIN_SCALE = 0.85f;
    protected static final float DEFAULT_CENTER = 0.5f;

    protected float minScale = DEFAULT_MIN_SCALE;
    @Override
    public void transformPage(@NonNull View page, float position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            page.setElevation(-Math.abs(position));
        }

        final int pageWidth = page.getWidth();
        final int pageHeight = page.getHeight();

        page.setPivotY(pageHeight / 2);
        page.setPivotX(pageWidth / 2);
        if (position < -1) {
            page.setScaleX(minScale);
            page.setScaleY(minScale);
            page.setPivotX(pageWidth);
        } else if (position <= 1) {
            if (position < 0) {
                final float scaleFactor = (1 + position) * (1 - minScale) + minScale;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(pageWidth * (DEFAULT_CENTER + DEFAULT_CENTER * -position));
            } else {
                final float scaleFactor = (1 - position) * (1 - minScale) + minScale;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
            }
        } else {
            page.setPivotX(0f);
            page.setScaleX(minScale);
            page.setScaleY(minScale);
        }
    }
}
