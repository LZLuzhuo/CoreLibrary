/* Copyright 2022 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_core.ui.recyclerview;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearSmoothScroller;

/**
 * Description: 系统的 LinearSmoothScroller 只支持 START / END / ANY 三种状态, LinearSmoothScroller2 在此基础上新增 CENTER 状态
 * @Author: Luzhuo
 * @Creation Date: 2022/5/24 9:57
 * @Copyright: Copyright 2022 Luzhuo. All rights reserved.
 **/
class LinearSmoothScroller2 extends LinearSmoothScroller {
    private final ScrollType type;
    private final float speed;

    public LinearSmoothScroller2(Context context, ScrollType type, float speed) {
        super(context);
        this.type = type;
        this.speed = speed;
    }

    @Override
    public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
        switch (type) {
            case Start:
                return boxStart - viewStart;
            case Center:
                return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
            case End:
                return boxEnd - viewEnd;
            case Any:
                final int dtStart = boxStart - viewStart;
                if (dtStart > 0) {
                    return dtStart;
                }
                final int dtEnd = boxEnd - viewEnd;
                if (dtEnd < 0) {
                    return dtEnd;
                }
                break;
        }
        return 0;
    }

    @Override
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        return speed / displayMetrics.densityDpi;
    }
}
