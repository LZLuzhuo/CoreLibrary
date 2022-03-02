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
package me.luzhuo.lib_core.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ListPopupWindow;
import me.luzhuo.lib_core.R;
import me.luzhuo.lib_core.app.color.ColorManager;

/**
 * Description: 下拉菜单框, 最小宽度与 AnchorView 的宽度一样, 如果内容过长而超出该宽度, 将以文本内容的宽度为准
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 18:07
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class SpinnerX extends ListPopupWindow {
    private Context context;
    private SpinnerXAdapter adapter;
    private OnSpinnerClickListener listener;
    private List<String> datas = new ArrayList<>();
    private int index = -1;
    private ColorManager color;
    private boolean limitHeight = false;

    public SpinnerX(@NonNull Context context) {
        this(context, false);
    }

    public SpinnerX(@NonNull Context context, boolean limitHeight) {
        super(context);
        this.context = context;
        this.limitHeight = limitHeight;
        color = new ColorManager(context);

        init();
    }

    private void init() {
        adapter = new SpinnerXAdapter();
        this.setAdapter(adapter);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setModal(true);
        this.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) listener.onMenu(datas.get(position), position);

                dismiss();
            }
        });
    }

    public void setData(@NonNull List<String> datas, int index) {
        this.index = index;
        setData(datas);
    }

    public void setData(@NonNull List<String> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    public void setIndex(int index) {
        this.index = index;
        if (adapter != null) adapter.notifyDataSetChanged();
    }

    public void setAnchorView(@NonNull View view) {
        final Resources res = context.getResources();
        int mPopupMaxWidth = Math.max(res.getDisplayMetrics().widthPixels / 2, res.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
        int individualMenuWidth = measureIndividualMenuWidth(adapter, null, context, mPopupMaxWidth);
        int viewWidth = view.getWidth();
        setContentWidth(Math.max(viewWidth, individualMenuWidth));

        if (limitHeight) {
            int mPopupMaxHeight = Math.max(res.getDisplayMetrics().heightPixels * 2 / 3, res.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
            int individualMenuHeight = measureIndividualMenuHeight(adapter, null, context, mPopupMaxHeight);
            setHeight(Math.min(mPopupMaxHeight, individualMenuHeight));
        }

        super.setAnchorView(view);
    }

    public void show() {
        if (getAnchorView() == null) throw new IllegalArgumentException("You must be called first setAnchorView(View).");
        if (adapter.getCount() <= 0) Log.w(SpinnerX.class.getSimpleName(), "WARRING: You did not call setData(List<String>) to set the data");

        super.show();
    }

    public interface OnSpinnerClickListener {
        public void onMenu(String context, int position);
    }
    public void setOnSpinnerClickListener(@Nullable OnSpinnerClickListener listener) {
        this.listener = listener;
    }

    protected static int measureIndividualMenuWidth(ListAdapter adapter, ViewGroup parent, Context context, int maxAllowedWidth) {
        // Menus don't tend to be long, so this is more sane than it looks.
        int maxWidth = 0;
        View itemView = null;
        int itemType = 0;

        final int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            final int positionType = adapter.getItemViewType(i);
            if (positionType != itemType) {
                itemType = positionType;
                itemView = null;
            }

            if (parent == null) {
                parent = new FrameLayout(context);
            }

            itemView = adapter.getView(i, itemView, parent);
            itemView.measure(widthMeasureSpec, heightMeasureSpec);

            final int itemWidth = itemView.getMeasuredWidth();
            if (itemWidth >= maxAllowedWidth) {
                return maxAllowedWidth;
            } else if (itemWidth > maxWidth) {
                maxWidth = itemWidth;
            }
        }

        return maxWidth;
    }

    protected static int measureIndividualMenuHeight(ListAdapter adapter, ViewGroup parent, Context context, int maxAllowedHeight) {
        // Menus don't tend to be long, so this is more sane than it looks.
        int maxHeight = 0;
        View itemView = null;
        int itemType = 0;

        final int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            final int positionType = adapter.getItemViewType(i);
            if (positionType != itemType) {
                itemType = positionType;
                itemView = null;
            }

            if (parent == null) {
                parent = new FrameLayout(context);
            }

            itemView = adapter.getView(i, itemView, parent);
            itemView.measure(widthMeasureSpec, heightMeasureSpec);

            final int itemHeight = itemView.getMeasuredHeight();
            if (itemHeight >= maxAllowedHeight) {
                return maxAllowedHeight;
            } else {
                maxHeight += itemHeight;
            }
        }

        return maxHeight;
    }

    private class SpinnerXAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                viewHolder = new ViewHolder();

                viewHolder.textView = convertView.findViewById(android.R.id.text1);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final String data = datas.get(position);

            viewHolder.textView.setText(data);
            if (position == index) viewHolder.textView.setTextColor(color.getColorAccent());
            else viewHolder.textView.setTextColor(color.getColor(R.color.core_list_popup_window_text_color));

            return convertView;
        }

        private class ViewHolder {
            TextView textView;
        }
    }
}
