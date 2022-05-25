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

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Description: 系统的 LinearSnapHelper 停下来只有 Center 模式, LinearSnapHelper2 对其添加了 Start / End / Any 模式
 * @Author: Luzhuo
 * @Creation Date: 2022/5/24 10:22
 * @Copyright: Copyright 2022 Luzhuo. All rights reserved.
 **/
class LinearSnapHelper2 extends LinearSnapHelper {
    private ScrollType type;

    // Orientation helpers are lazily created per LayoutManager.
    @Nullable
    private OrientationHelper mVerticalHelper;
    @Nullable
    private OrientationHelper mHorizontalHelper;

    public LinearSnapHelper2(ScrollType type) {
        this.type = type;
    }

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];

        if (layoutManager.canScrollHorizontally()) {

            if (type == ScrollType.Start) out[0] = distanceToStart(layoutManager, targetView, getHorizontalHelper(layoutManager));
            else if (type == ScrollType.Center) out[0] = distanceToCenter(layoutManager, targetView, getHorizontalHelper(layoutManager));
            else if (type == ScrollType.End) out[0] = distanceToEnd(layoutManager, targetView, getHorizontalHelper(layoutManager));
            else if (type == ScrollType.Any) out[0] = distanceToAny(layoutManager, targetView, getHorizontalHelper(layoutManager));

        } else {
            out[0] = 0;
        }

        if (layoutManager.canScrollVertically()) {

            if (type == ScrollType.Start) out[0] = distanceToStart(layoutManager, targetView, getVerticalHelper(layoutManager));
            else if (type == ScrollType.Center) out[0] = distanceToCenter(layoutManager, targetView, getVerticalHelper(layoutManager));
            else if (type == ScrollType.End) out[0] = distanceToEnd(layoutManager, targetView, getVerticalHelper(layoutManager));
            else if (type == ScrollType.Any) out[0] = distanceToAny(layoutManager, targetView, getVerticalHelper(layoutManager));

        } else {
            out[1] = 0;
        }

        return out;
    }

    @Override
    @Nullable
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            if (type == ScrollType.Start) return findStartView(layoutManager, getVerticalHelper(layoutManager));
            else if (type == ScrollType.Center) return findCenterView(layoutManager, getVerticalHelper(layoutManager));
            else if (type == ScrollType.End) return findEndView(layoutManager, getVerticalHelper(layoutManager));
            else if (type == ScrollType.Any) return findAnyView(layoutManager, getVerticalHelper(layoutManager));
        } else if (layoutManager.canScrollHorizontally()) {
            if (type == ScrollType.Start) return findStartView(layoutManager, getHorizontalHelper(layoutManager));
            else if (type == ScrollType.Center) return findCenterView(layoutManager, getHorizontalHelper(layoutManager));
            else if (type == ScrollType.End) return findEndView(layoutManager, getHorizontalHelper(layoutManager));
            else if (type == ScrollType.Any) return findAnyView(layoutManager, getHorizontalHelper(layoutManager));
        }
        return null;
    }

    protected int distanceToCenter(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView, OrientationHelper helper) {
        final int childCenter = helper.getDecoratedStart(targetView) + (helper.getDecoratedMeasurement(targetView) / 2);
        final int containerCenter = helper.getStartAfterPadding() + helper.getTotalSpace() / 2;
        return childCenter - containerCenter;
    }

    protected int distanceToStart(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView, OrientationHelper helper) {
        final int childStart = helper.getDecoratedStart(targetView);
        final int containerStart = helper.getStartAfterPadding();
        return childStart - containerStart;
    }

    protected int distanceToEnd(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView, OrientationHelper helper) {
        final int childCenter = helper.getDecoratedStart(targetView) + (helper.getDecoratedMeasurement(targetView));
        final int containerCenter = helper.getStartAfterPadding() + helper.getTotalSpace();
        return childCenter - containerCenter;
    }

    protected int distanceToAny(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView, OrientationHelper helper) {
        return 0;
    }

    @Nullable
    protected View findCenterView(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }

        View closestChild = null;
        final int center = helper.getStartAfterPadding() + helper.getTotalSpace() / 2;
        int absClosest = Integer.MAX_VALUE;

        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            int childCenter = helper.getDecoratedStart(child) + (helper.getDecoratedMeasurement(child) / 2);
            int absDistance = Math.abs(childCenter - center);

            /** if child center is closer than previous closest, set it as closest  **/
            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }
        return closestChild;
    }

    protected View findStartView(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }

        View closestChild = null;
        final int start = helper.getStartAfterPadding();
        int absClosest = Integer.MAX_VALUE;

        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            int childStart = helper.getDecoratedStart(child);
            int absDistance = Math.abs(childStart - start);

            /** if child center is closer than previous closest, set it as closest  **/
            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }
        return closestChild;
    }

    protected View findEndView(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return null;
        }

        View closestChild = null;
        final int end = helper.getStartAfterPadding() + helper.getTotalSpace();
        int absClosest = Integer.MAX_VALUE;

        for (int i = 0; i < childCount; i++) {
            final View child = layoutManager.getChildAt(i);
            int childCenter = helper.getDecoratedStart(child) + (helper.getDecoratedMeasurement(child) / 2);
            int absDistance = Math.abs(childCenter - end);

            /** if child center is closer than previous closest, set it as closest  **/
            if (absDistance < absClosest) {
                absClosest = absDistance;
                closestChild = child;
            }
        }
        return closestChild;
    }

    protected View findAnyView(RecyclerView.LayoutManager layoutManager, OrientationHelper helper) {
        return null;
    }

    @NonNull
    protected OrientationHelper getVerticalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;
    }

    @NonNull
    protected OrientationHelper getHorizontalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }
}
