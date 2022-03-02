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
package me.luzhuo.lib_core.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_core.R;
import me.luzhuo.lib_core.app.color.ColorManager;

/**
 * Description: 从左到右, 按比例分成3列的列表
 * @Author: Luzhuo
 * @Creation Date: 2021/3/14 17:58
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class MoreListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MoreListBean> mDatas = new ArrayList<>();
    private Context context;
    private int type;
    private boolean haveDefaultChecked;
    public static final int TYPE_One = 1, TYPE_Two = 2, TYPE_Tree = 3;
    private MoreListListener listener;

    @IntDef({TYPE_One, TYPE_Two, TYPE_Tree})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MoreListAdapterType {}

    private static final int OneBackgroudColorNormal = 0xFFF0F0F0;
    private static final int OneBackgroudColorChecked = 0xFFF8F8F8;
    private static final int OneTextColorNormal = 0xFF333333;
    private static int OneTextColorChecked;

    private static final int TwoBackgroudColorNormal = OneBackgroudColorChecked;
    private static final int TwoBackgroudColorChecked = 0xFFFFFFFF;
    private static final int TwoTextColorNormal = OneTextColorNormal;
    private static int TwoTextColorChecked;

    private static final int TreeBackgroudColorNormal = TwoBackgroudColorChecked;
    private static final int TreeBackgroudColorChecked = TwoBackgroudColorChecked;
    private static final int TreeTextColorNormal = OneTextColorNormal;
    private static int TreeTextColorChecked;

    public MoreListAdapter(@NonNull Context context, @MoreListAdapterType int type, boolean haveDefaultChecked) {
        this.context = context;
        this.type = type;
        this.haveDefaultChecked = haveDefaultChecked;
        final ColorManager color = new ColorManager(context);

        {
            OneTextColorChecked = color.getColorAccent();
            TwoTextColorChecked = color.getColorAccent();
            TreeTextColorChecked = color.getColorAccent();
        }
    }

    public MoreListAdapter(@NonNull Context context) {
        this(context, TYPE_Tree, false);
    }

    public void setData(List<MoreListBean> datas) {
        if (datas == null || datas.size() <= 0) return;
        this.mDatas.addAll(datas);

        if (haveDefaultChecked) this.mDatas.get(0).isChecked = true;
        notifyDataSetChanged();
    }

    public void clear() {
        this.mDatas.clear();
        notifyDataSetChanged();
    }

    public void setCurrent(int current) {
        if (current > mDatas.size() - 1) return;

        // single
        for (MoreListBean mData : mDatas) { mData.isChecked = false; }
        mDatas.get(current).isChecked = true;
        notifyDataSetChanged();
    }

    public void setMoreListListener(@NonNull MoreListListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder(LayoutInflater.from(context).inflate(R.layout.core_item_more_list, parent, false));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecyclerHolder) holder).bindData(mDatas.get(position));
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView more_list_title;
        public RelativeLayout more_list_parent;

        public RecyclerHolder(View itemView) {
            super(itemView);
            more_list_title = itemView.findViewById(R.id.more_list_title);
            more_list_parent = itemView.findViewById(R.id.more_list_parent);

            more_list_parent.setOnClickListener(this);
        }

        public void bindData(MoreListBean data) {

            final boolean checked = data.isChecked;
            switch (type) {
                case TYPE_One:
                    if (checked) {
                        more_list_title.setTextColor(OneTextColorChecked);
                        more_list_parent.setBackgroundColor(OneBackgroudColorChecked);
                    } else {
                        more_list_title.setTextColor(OneTextColorNormal);
                        more_list_parent.setBackgroundColor(OneBackgroudColorNormal);
                    }
                    break;
                case TYPE_Two:
                    if (checked) {
                        more_list_title.setTextColor(TwoTextColorChecked);
                        more_list_parent.setBackgroundColor(TwoBackgroudColorChecked);
                    } else {
                        more_list_title.setTextColor(TwoTextColorNormal);
                        more_list_parent.setBackgroundColor(TwoBackgroudColorNormal);
                    }
                    break;
                case TYPE_Tree:
                    if (checked) {
                        more_list_title.setTextColor(TreeTextColorChecked);
                        more_list_parent.setBackgroundColor(TreeBackgroudColorChecked);
                    } else {
                        more_list_title.setTextColor(TreeTextColorNormal);
                        more_list_parent.setBackgroundColor(TreeBackgroudColorNormal);
                    }
                    break;
            }

            more_list_title.setText(data.title);
        }

        @Override
        public void onClick(View v) {
            setCurrent(getLayoutPosition());
            if (listener != null) listener.onSelect(getLayoutPosition(), mDatas.get(getLayoutPosition()));
        }
    }

    // ================================= 供外部使用的类 =================================

    public static class MoreListBean {
        public String title;
        public boolean isChecked = false;
        public Object bundle;

        public MoreListBean(String title, boolean isChecked, Object bundle) {
            this.title = title;
            this.isChecked = isChecked;
            this.bundle = bundle;
        }

        public MoreListBean(String title) {
            this.title = title;
        }
    }

    public static interface MoreListListener {
        public void onSelect(int position, MoreListBean data);
    }
}

