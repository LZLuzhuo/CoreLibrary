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
package me.luzhuo.lib_core.ui.recyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Description: 实现了自动显示 EmptyView 的 RecyclerView
 * @Author: Luzhuo
 * @Creation Date: 2021/5/22 0:31
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class EmptyRecyclerView extends RecyclerView {
    private Adapter adapter;
    public Adapter emptyAdapter = new EmptyAdapter();

    public EmptyRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        adapter.registerAdapterDataObserver(new AdapterDataObserver() {
            @Override
            public void onChanged() {
                checkEmptyData();
            }
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                checkEmptyData();
            }
            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                checkEmptyData();
            }
        });
        checkEmptyData();
    }

    private void checkEmptyData() {
        final int count = adapter.getItemCount();
        if (count > 0) super.setAdapter(adapter);
        else super.setAdapter(emptyAdapter);
    }
}
