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
package me.luzhuo.lib_core.ui.widget.tagbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_core.R;
import me.luzhuo.lib_core.ui.calculation.UICalculation;
import me.luzhuo.lib_core.ui.recyclerview.RecyclerManager;

/**
 * Description: 用于选择的标签按钮
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/11/11 10:32
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class TagButtonView extends RecyclerView {
    private RecyclerManager recManager;
    private UICalculation ui;
    private TagButtonAdapter adapter;

    public TagButtonView(@NonNull Context context) {
        this(context, null);
    }

    public TagButtonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagButtonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        recManager = new RecyclerManager(context);
        ui = new UICalculation(context);

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.TagButtonView, defStyleAttr, 0);
        int span_count;
        float interval;
        boolean single;
        try {
            span_count = typedArray.getInt(R.styleable.TagButtonView_core_span_count, 4); // 跨度个数
            interval = typedArray.getDimension(R.styleable.TagButtonView_core_interval, -1); // 间隔
            single = typedArray.getBoolean(R.styleable.TagButtonView_core_single, true);
        } finally {
            typedArray.recycle();
        }


        this.setOverScrollMode(OVER_SCROLL_NEVER);
        GridLayoutManager layoutManager = new GridLayoutManager(context, span_count);
        this.setLayoutManager(layoutManager);
        recManager.setItemDecorationOnLinearLayout(this, interval == -1 ? 8 : ui.px2dp(interval));

        adapter = new TagButtonAdapter(context, single);
        this.setAdapter(adapter);
    }

    public void setTagButtonListener(TagButtonListener listener) {
        adapter.setTagButtonListener(listener);
    }

    public void setDatas(List<TagButton> datas) {
        adapter.setData(datas);
    }

    public List<TagButton> getData() {
        return adapter.getData();
    }
}
