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
package me.luzhuo.lib_core.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_core.R;

/**
 * Description: Settings的适配器
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 17:13
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class SettingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SettingBean> mDatas;
    private Context context;
    private static final int TYPE_Data = 1, TYPE_Other = 2;
    private int dataLayout, lineLayout;
    private OnSettingCallback callback;

    public SettingsAdapter(OnSettingCallback callback, List<SettingBean> data) {
        this(callback, data, R.layout.core_item_setting, R.layout.core_item_line);
    }

    public SettingsAdapter(OnSettingCallback callback, List<SettingBean> data, @LayoutRes int dataRes, @LayoutRes int lineRes) {
        this.callback = callback;
        this.mDatas = data;
        this.dataLayout = dataRes;
        this.lineLayout = lineRes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        if (viewType == TYPE_Data) return new SettingHolder(LayoutInflater.from(parent.getContext()).inflate(dataLayout, parent, false));
        return new LineHolder(LayoutInflater.from(parent.getContext()).inflate(lineLayout, parent, false));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        final SettingBean data = mDatas.get(position);

        if (data.leftIcon == 0 && TextUtils.isEmpty(data.leftName) && data.rightIcon == 0 && TextUtils.isEmpty(data.rightName)) return TYPE_Other;
        else return TYPE_Data;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);

        if (viewType == TYPE_Data) ((SettingHolder) holder).bindData(mDatas.get(position));
    }

    private class SettingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView leftIcon;
        private TextView leftName;
        private ImageView rightIcon;
        private TextView rightName;
        private View zone;

        public SettingHolder(View itemView) {
            super(itemView);

            leftIcon = itemView.findViewById(R.id.core_settings_lefticon);
            leftName = itemView.findViewById(R.id.core_settings_leftname);
            rightIcon = itemView.findViewById(R.id.core_settings_righticon);
            rightName = itemView.findViewById(R.id.core_settings_rightname);
            zone = itemView.findViewById(R.id.core_settings_zone);

            zone.setOnClickListener(this);
        }

        public void bindData(SettingBean data) {
            if (data.leftIcon > 0) {
                leftIcon.setVisibility(View.VISIBLE);
                leftIcon.setImageResource(data.leftIcon);
            } else leftIcon.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(data.leftName)) leftName.setText(data.leftName);
            else leftName.setText("");

            if (data.rightIcon > 0) {
                rightIcon.setVisibility(View.VISIBLE);
                rightIcon.setImageResource(data.rightIcon);
            } else rightIcon.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(data.rightName)) rightName.setText(data.rightName);
            else rightName.setText("");
        }

        @Override
        public void onClick(View v) {
            if (callback != null) callback.onSettingCallback(mDatas.get(getLayoutPosition()));
        }
    }

    private static class LineHolder extends RecyclerView.ViewHolder {
        public LineHolder(View itemView) { super(itemView); }
    }

    // ================================= 供外部使用的类 =================================

    public static class SettingBean {
        private int leftIcon;
        private String leftName;
        private int rightIcon;
        private String rightName;

        /**
         * 全部属性都为空, 该条目为分隔线
         */
        public SettingBean() {
            this.leftIcon = 0;
            this.leftName = null;
            this.rightIcon = 0;
            this.rightName = null;
        }

        public SettingBean(int leftIcon, String leftName, int rightIcon, String rightName) {
            this.leftIcon = leftIcon;
            this.leftName = leftName;
            this.rightIcon = rightIcon;
            this.rightName = rightName;
        }

        public String value() {
            return leftName;
        }
    }

    public static interface OnSettingCallback {
        public void onSettingCallback(SettingBean data);
    }
}

