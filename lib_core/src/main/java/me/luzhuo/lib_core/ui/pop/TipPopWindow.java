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
package me.luzhuo.lib_core.ui.pop;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * Description: 显示在View上方的Tip弹窗
 * 一般用于:
 *  1. IM长按后的菜单
 *  2. View上方的提示框
 * @Author: Luzhuo
 * @Creation Date: 2021/4/19 1:16
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class TipPopWindow extends PopWindowManager {
    protected int padding = 0;
    public TipPopWindow(@NonNull Context context, @NonNull View view) {
        super(context, view, From.TOP, ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setPadding(5);
    }

    public TipPopWindow(@NonNull Context context, int id) {
        this(context, LayoutInflater.from(context).inflate(id, null, false));
    }

    public void setPadding(int paddingDp) {
        this.padding = ui.dp2px(paddingDp);
    }

    @Override
    public void show() {
        if (this.parentView == null) {
            Log.e(TAG, "请先调用 setAnchorView() 设置锚点View");
        } else {
            try {
                if (this.from == From.TOP) {
                    int[] location = new int[2];
                    parentView.getLocationInWindow(location);
                    getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                    final int x = location[0] - (Math.abs(parentView.getWidth() - getContentView().getMeasuredWidth()) / 2);
                    final int y = location[1] - getContentView().getMeasuredHeight() + padding;
                    this.showAtLocation(parentView, Gravity.START|Gravity.TOP, x, y);
                }

                this.backgroundAlpha(0.5F);
            } catch (WindowManager.BadTokenException var2) {
                var2.printStackTrace();
                Log.e(TAG, "请不要在 Activity 启动时直接调用!");
            }
        }
    }
}
