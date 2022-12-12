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

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.luzhuo.lib_core.ui.dialog.BottomDialog.OnMenuItemClick;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_core.R;

/**
 * Description: 从底部弹出的 BottomMenu 菜单适配器
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 17:30
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class BottomMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int layoutId;
    private List<String> menus;
    private List<Integer> colors;
    private OnMenuItemClick onMenuItemClick;

    public BottomMenuAdapter(@LayoutRes int layoutId, @NonNull List<String> menus, @Nullable OnMenuItemClick onMenuItemClick) {
        this(layoutId, menus, null, onMenuItemClick);
    }

    public BottomMenuAdapter(@LayoutRes int layoutId, @NonNull List<String> menus, @Nullable List<Integer> colors, @Nullable OnMenuItemClick onMenuItemClick) {
        this.layoutId = layoutId;
        this.menus = menus;
        this.colors = colors;
        this.onMenuItemClick = onMenuItemClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemHolder) holder).bindData(menus.get(position));
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView menuTitle;
        private View menuParent;

        private ItemHolder(View itemView) {
            super(itemView);
            menuTitle = itemView.findViewById(R.id.core_bottom_dialog_meme_title);
            menuParent = itemView.findViewById(R.id.core_bottom_dialog_menu_parent);

            menuParent.setOnClickListener(this);
        }

        private void bindData(String data) {
            menuTitle.setText(data);
            if(colors != null && colors.size() == menus.size()) menuTitle.setTextColor(colors.get(getLayoutPosition()));
        }

        @Override
        public void onClick(View v) {
            if(onMenuItemClick != null) onMenuItemClick.onItemClick(menus, getLayoutPosition(), menus.get(getLayoutPosition()));
        }
    }
}
