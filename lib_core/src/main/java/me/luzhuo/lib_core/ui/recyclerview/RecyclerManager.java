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
package me.luzhuo.lib_core.ui.recyclerview;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.google.android.flexbox.FlexboxLayoutManager;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import me.luzhuo.lib_core.math.calculation.MathCalculation;
import me.luzhuo.lib_core.ui.calculation.UICalculation;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Description:
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/4/13 9:42
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class RecyclerManager {
    private Context context;
    private UICalculation ui;
    private MathCalculation math = new MathCalculation();
    private final int LinearLayoutManagerType = 0x001, GridLayoutManagerType = 0x002, StaggeredGridLayoutManagerType = 0x003, FlexboxLayoutManagerType = 0x004;

    public RecyclerManager(@NonNull Context context) {
        if(context == null) throw new NullPointerException("Context is not null when creating RecyclerViewManager.");
        this.context = context;
        this.ui = new UICalculation(context);
    }

    /**
     * 设置Adapter边距
     * 如果使用了 FlexboxLayoutManager, 需要设置 maxLines 和 layout_margin
     * 如果使用了 CardView, 请使用 layout_margin 进行分隔, 不建议使用该方法
     *
     * Set the top, bottom, left, and right margins for the RecyclerView Item View.
     * Since FlexboxLayoutManager is an informal Google library, you need to set maxLines and layout_margin for TextView.
     * If you use CardView layout, please use layout_margin to separate, it is not recommended to call this method.
     * principle:
     * <p><img src="http://luzhuo.me/luzhuoCode/lib_common_ui/manager/RecyclerView/principle.png"/></p>
     *
     * Screenshot:
     * 1.LinearLayoutManager
     * <p><img src="http://luzhuo.me/luzhuoCode/lib_common_ui/manager/RecyclerView/LinearLayoutManager.jpg" /></p>
     *
     * 2.GridLayoutManager
     * <p><img src="http://luzhuo.me/luzhuoCode/lib_common_ui/manager/RecyclerView/GridLayoutManager.jpg" /></p>
     *
     * 3.StaggeredGridLayoutManager
     * <p><img src="http://luzhuo.me/luzhuoCode/lib_common_ui/manager/RecyclerView/StaggeredGridLayoutManager.jpg" /></p>
     *
     * 4.FlexboxLayoutManager
     * <p><img src="http://luzhuo.me/luzhuoCode/lib_common_ui/manager/RecyclerView/FlexboxLayoutManager.jpg" /></p>
     *
     * Use;
     * <pre>
     * 1.LinearLayoutManager
     * recyclerView.setLayoutManager(new LinearLayoutManager(this));
     * recyclerView.setAdapter(new Adapter(this));
     * recyclerViewManager.setItemDecorationOnLinearLayout(recyclerView, 10);
     *
     * 2.GridLayoutManager
     * recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
     * recyclerView.setAdapter(new Adapter(this));
     * recyclerViewManager.setItemDecorationOnLinearLayout(recyclerView, 10);
     *
     * 3.StaggeredGridLayoutManager
     * recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
     * recyclerView.setAdapter(new Adapter(this));
     * recyclerViewManager.setItemDecorationOnLinearLayout(recyclerView, 10);
     *
     * 4.FlexboxLayoutManager
     * recyclerView.setLayoutManager(new FlexboxLayoutManager(this));
     * recyclerView.setAdapter(new Adapter(this));
     * recyclerViewManager.setItemDecorationOnLinearLayout(recyclerView, 10);
     * </pre>
     *
     * @param recyclerView androidx.recyclerview.widget.RecyclerView
     * @param dp Margins in dp.
     */
    public void setItemDecorationOnLinearLayout(@Nullable RecyclerView recyclerView, final float dp){
        if(recyclerView == null || dp <= 0) return;
        final int margin = ui.dp2px(dp);

        final int layoutManagerType = getLayoutMangerType(recyclerView.getLayoutManager());
        // Only GridLayoutManagerType and StaggeredGridLayoutManagerType have SpanceCount, LinearLayoutManagerType has no SpanceCount.
        final int spanceCount = getSpanceCount(recyclerView.getLayoutManager());

        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int childPosition = parent.getChildAdapterPosition(view);

                if (layoutManagerType == GridLayoutManagerType || layoutManagerType == StaggeredGridLayoutManagerType) {
                    int spanIndex;
                    if(layoutManagerType == StaggeredGridLayoutManagerType) spanIndex = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
                    else spanIndex = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();

                    boolean isRight = spanIndex == spanceCount - 1;
                    boolean isLeft = spanIndex == 0;

                    if (childPosition < spanceCount) {
                        outRect.set(isLeft ? margin : margin / 2, margin, isRight ? margin : margin / 2, margin);
                    } else {
                        outRect.set(isLeft ? margin : margin / 2, 0, isRight ? margin : margin / 2, margin);
                    }
                } else if (layoutManagerType == FlexboxLayoutManagerType) {

                    if (childPosition < spanceCount) {
                        outRect.set(margin, margin, 0, margin);
                    } else {
                        outRect.set(margin, 0, 0, margin);
                    }
                } else {
                    // If it is LinearLayoutManagerType or other type.
                    if (childPosition == 0) {
                        /*
                        the first child view
                        int left, int top, int right, int bottom
                        */
                        outRect.set(margin, margin, margin, margin);
                    } else {
                        outRect.set(margin, 0, margin, margin);
                    }
                }
            }
        };
        recyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * Only GridLayoutManagerType and StaggeredGridLayoutManagerType have SpanceCount, LinearLayoutManagerType has no SpanceCount.
     * @param layoutManager GridLayoutManagerType or StaggeredGridLayoutManagerType.
     * @return return SpanceCount.
     *         return 0 if it is other type.
     */
    private int getSpanceCount(@NonNull RecyclerView.LayoutManager layoutManager) {
        int layoutMangerType = getLayoutMangerType(layoutManager);
        if(layoutMangerType == GridLayoutManagerType){
            return ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutMangerType == StaggeredGridLayoutManagerType) {
            return ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return 0;
    }

    /**
     * Get the type of RecyclerView's LayoutManager. If it is not a standard LayoutManager, it returns LinearLayoutManager.
     * @param layoutManager RecyclerView's LayoutManager
     * @return return LinearLayoutManager or GridLayoutManager or StaggeredGridLayoutManager.
     */
    private int getLayoutMangerType(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            return GridLayoutManagerType;
        } else if(layoutManager instanceof StaggeredGridLayoutManager) {
            return StaggeredGridLayoutManagerType;
        } else if(layoutManager instanceof LinearLayoutManager){
            return LinearLayoutManagerType;
        } else if (layoutManager instanceof FlexboxLayoutManager) {
            return FlexboxLayoutManagerType;
        }
        // if it is LinearLayoutManager or other type.
        return LinearLayoutManagerType;
    }

    /**
     * 是否滚到了底部位置
     *
     * If RecyclerView scroll to the bottom, it will return true.
     * Determine whether it is the bottom, which is based on the last View, it return true for the last View shows half.
     *
     * Use:
     * <pre>
     * recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
     *     public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
     *         if (recyclerViewManager.isScrollBottom(recyclerView)) {
     *             // todo_ load more data
     *         }
     *     }
     * });
     * </pre>
     *
     * <p><img src="http://luzhuo.me/luzhuoCode/lib_common_ui/manager/RecyclerView/isScrollBottom.png" /></p>
     *
     * @param recyclerView androidx.recyclerview.widget.RecyclerView
     * @return Return true if the last View is displayed.
     */
    public boolean isScrollBottom(@NonNull RecyclerView recyclerView){
        return isScrollBottom(recyclerView, 0);
    }

    /**
     * 是否滚到了 底部+marginCont 的位置
     *
     * When RecyclerView scrolls to the bottom position - marginCount position, it starts to return true, all subsequent views will return true, please handle it yourself
     * {@link #isScrollBottom(RecyclerView)}
     *
     * <p><img src="http://luzhuo.me/luzhuoCode/lib_common_ui/manager/RecyclerView/isScrollBottom_marginCont.png" /></p>
     *
     * @param recyclerView androidx.recyclerview.widget.RecyclerView
     * @param marginCount
     * @return Return true if the last View is displayed.
     */
    public boolean isScrollBottom(@NonNull RecyclerView recyclerView, int marginCount){
        if(recyclerView == null || marginCount < 0) return false;

        int lastVisibleItemPosition = 0;
        int visibleItemCount = 0;
        int totalItemCount = 0;

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            GridLayoutManager manager = (GridLayoutManager)layoutManager;

            lastVisibleItemPosition = manager.findLastVisibleItemPosition();
            visibleItemCount = manager.getChildCount();
            totalItemCount = manager.getItemCount();

        } else if(layoutManager instanceof StaggeredGridLayoutManager){
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager)layoutManager;

            lastVisibleItemPosition = math.max(manager.findLastVisibleItemPositions(new int[manager.getSpanCount()]));
            visibleItemCount = manager.getChildCount();
            totalItemCount = manager.getItemCount();

        } else if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;

            lastVisibleItemPosition = manager.findLastVisibleItemPosition();
            visibleItemCount = manager.getChildCount();
            totalItemCount = manager.getItemCount();

        } else if (layoutManager instanceof FlexboxLayoutManager) {
            FlexboxLayoutManager manager = (FlexboxLayoutManager) layoutManager;

            lastVisibleItemPosition = manager.findLastVisibleItemPosition();
            visibleItemCount = manager.getChildCount();
            totalItemCount = manager.getItemCount();

        } else {
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;

            lastVisibleItemPosition = manager.findLastVisibleItemPosition();
            visibleItemCount = manager.getChildCount();
            totalItemCount = manager.getItemCount();
        }


        if(lastVisibleItemPosition == 0 && visibleItemCount == 0 && totalItemCount == 0)
            return false;

        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition >= totalItemCount - 1 - marginCount && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }

    private View oldView = null;
    /**
     * 检测 RecyclerView 内的 Item 的某个 View控件 是否可见
     * @param recyclerView RecyclerView
     */
    @Deprecated()
    public void checkItemViewVisibility(@NonNull final RecyclerView recyclerView, @NonNull final LinearLayoutManager layoutManager, @IdRes final int resId /* 资源id */, @Nullable final OnItemViewVisibilityCallback callback) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int visibleCount = 0;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == SCROLL_STATE_IDLE) startCheckVisibility(recyclerView, visibleCount);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                final int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                final int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                visibleCount = lastVisibleItem - firstVisibleItem + 1;
            }

            private void startCheckVisibility(RecyclerView recyclerView, int visibleCount) {
                for (int i = 0; i < visibleCount; i++) {
                    if (recyclerView.getChildAt(i) == null) break;
                    final View newView = recyclerView.getChildAt(i).findViewById(resId);
                    if (newView == null) break;

                    final Rect rect = new Rect();
                    newView.getLocalVisibleRect(rect);
                    if (rect.top == 0 && rect.bottom == newView.getHeight()) {
                        if (oldView == newView) return;

                        if (callback != null) {
                            callback.onItemViewVisibility(false, oldView);
                            callback.onItemViewVisibility(true, newView);
                        }

                        oldView = newView;
                        return;
                    }
                }
            }
        });
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                if (recyclerView.getChildCount() <= 0) return;
                // 自动开始播放第一个
                final View firstView = recyclerView.getChildAt(0).findViewById(resId);
                oldView = firstView;

                if (callback != null) callback.onItemViewVisibility(true, firstView);
            }
        });
    }

    public interface OnItemViewVisibilityCallback {
        /**
         * 检测 RecyclerView 内的 Item 的某个 View控件 是否可见的回调
         * @param isVisible true 可见, false 不可见
         */
        public void onItemViewVisibility(boolean isVisible, View view);
    }

    public enum ScrollType {
        /**
         * 每次都滚动到顶部
         */
        Start,
        /**
         * 每次到滚动到底部
         */
        End,
        /**
         * 在视野范围内不滚动
         * 不在视野范围内, 将其移到视野范围内, 位置可能在顶部页可能在底部, 有原来的位置在上方还是在下方决定的
         */
        Any
    }

    /**
     * 垂直方向的滚动
     * @param type ScrollType
     * @param position 滚动到指定位置
     */
    public void scroll(@NonNull RecyclerView recyclerView, @NonNull final ScrollType type, int position) {
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(context) {
            @Override protected int getVerticalSnapPreference() {
                switch (type) {
                    case Start:
                        return LinearSmoothScroller.SNAP_TO_START;
                    case End:
                        return LinearSmoothScroller.SNAP_TO_END;
                    default:
                        return LinearSmoothScroller.SNAP_TO_ANY;
                }
            }
        };
        smoothScroller.setTargetPosition(position);
        recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
    }
}
