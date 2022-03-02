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
package me.luzhuo.lib_core.ui.dialog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_core.R;
import me.luzhuo.lib_core.ui.widget.rightmenu.OnMenuCallback;

/**
 * Description: 从中间弹出的 Dialog Menu 适配器
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 17:31
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class DialogMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private String[] mDatas;
    private int layout;
    private OnMenuCallback callback;

    public DialogMenuAdapter(@NonNull String[] menus, @Nullable OnMenuCallback callback) {
        this(R.layout.core_item_dialog_menu, menus, callback);
    }

    public DialogMenuAdapter(@LayoutRes int itemLayout, @NonNull String[] menus, @Nullable OnMenuCallback callback) {
        this.layout = itemLayout;
        this.mDatas = menus;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public int getItemCount() {
        return mDatas.length;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemHolder) holder).bindData(mDatas[position]);
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View dialog_menu_zone;
        private TextView core_dialog_menu;

        public ItemHolder(View itemView) {
            super(itemView);
            dialog_menu_zone = itemView.findViewById(R.id.dialog_menu_zone);

            core_dialog_menu = itemView.findViewById(R.id.core_dialog_menu);
            dialog_menu_zone.setOnClickListener(this);
        }

        public void bindData(String data) {
            core_dialog_menu.setText(data);
        }

        @Override
        public void onClick(View v) {
            if (callback != null) callback.onMenu(getLayoutPosition(), mDatas[getLayoutPosition()]);
        }
    }
}
