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

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import me.luzhuo.lib_core.R;

/**
 * Description: 水印管理
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 18:07
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class WaterMarkManager {
    private WaterMarkManager(){}
    public static WaterMarkManager instance(){
        return WaterMarkManager.Instance.instance;
    }
    private static class Instance{
        private static final WaterMarkManager instance = new WaterMarkManager();
    }

    /**
     * 添加水印到 Activity 界面
     * @param waterMarkContent 水印文本
     */
    public void addWatermark2Activity(@NonNull Activity activity, @NonNull String waterMarkContent, @LayoutRes int layout) {
        if (TextUtils.isEmpty(waterMarkContent)) return;

        View view = LayoutInflater.from(activity).inflate(layout, null, false);
        ViewGroup commin_shuiyin = (ViewGroup)view;
        for (int i = 0; i < commin_shuiyin.getChildCount(); i++) {
            View child = ((ViewGroup) commin_shuiyin).getChildAt(i);
            if (child instanceof TextView) ((TextView) child).setText(waterMarkContent);
        }
        activity.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void addWatermark2Activity(@NonNull Activity activity, @NonNull String waterMarkContent) {
        this.addWatermark2Activity(activity, waterMarkContent, R.layout.core_layout_watermark);
    }
}
