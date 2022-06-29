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
package me.luzhuo.lib_core.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import me.luzhuo.lib_core.R;
import me.luzhuo.lib_core.ui.calculation.UICalculation;

/**
 * Description: 从底部弹出的Dialog, 与 BottomDialog 的区别在于, 不能下滑关闭
 * @Author: Luzhuo
 * @Creation Date: 2021/9/2 21:20
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class BottomDialog2 {
    private final Context context;
    private final Dialog dialog;
    private final UICalculation ui;

    public BottomDialog2(@NonNull Context context, @NonNull View layout) {
        this(context, true, layout);
    }

    /**
     * 底部弹窗, 固定, 不可下滑关闭
     * @param cancelable 是否将外部的点击视为取消操作
     * @param layout 自定义布局
     */
    public BottomDialog2(@NonNull Context context, boolean cancelable, @NonNull View layout) {
        this.ui = new UICalculation(context);
        this.context = context;
        this.dialog = new Dialog(context, R.style.Core_Bottom_Dialog);
        this.dialog.setCanceledOnTouchOutside(cancelable);
        this.dialog.setContentView(layout);

        Window window = this.dialog.getWindow();
        window.setGravity(80);
        window.setWindowAnimations(R.style.Core_Bottom_Dialog_UpOrDownAnimation);
    }

    /**
     * 显示弹窗
     */
    public BottomDialog2 show() {
        this.dialog.show();
        return this;
    }

    /**
     * 默认按屏幕的80%显示弹窗
     */
    public BottomDialog2 showAllHeight() {
        this.dialog.show();
        this.setHeight(1f, 0.8f);
        return this;
    }

    /**
     * 关闭弹窗
     */
    public BottomDialog2 dismiss() {
        this.dialog.dismiss();
        return this;
    }

    /**
     * 设置高度
     * @param heightPercent 相对于屏幕的百分比
     */
    public BottomDialog2 setHeight(float withPercent, float heightPercent) {
        Display defaultDisplay = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        WindowManager.LayoutParams attributes = this.dialog.getWindow().getAttributes();
        attributes.width = (int) (((float) defaultDisplay.getWidth()) * withPercent);
        attributes.height = (int) (((float) defaultDisplay.getHeight()) * heightPercent);
        this.dialog.getWindow().setAttributes(attributes);
        return this;
    }

    public BottomDialog2 setHeight(int withDp, int heightDp) {
        WindowManager.LayoutParams attributes = this.dialog.getWindow().getAttributes();
        attributes.width = ui.dp2px(withDp);
        attributes.height = ui.dp2px(heightDp);
        this.dialog.getWindow().setAttributes(attributes);
        return this;
    }
}
