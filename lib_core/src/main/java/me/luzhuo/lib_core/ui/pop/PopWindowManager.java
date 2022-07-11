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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import me.luzhuo.lib_core.R;
import me.luzhuo.lib_core.ui.calculation.UICalculation;

/**
 * Description: PopWindow 管理类
 * @Author: Luzhuo
 * @Creation Date: 2021/3/14 17:45
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class PopWindowManager extends PopupWindow {
    protected static final String TAG = PopWindowManager.class.getSimpleName();
    protected Context context;
    protected From from;
    protected View parentView;
    protected FragmentActivity activity;
    protected UICalculation ui;

    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

    /**
     * @param view 用于展示的View
     * @param from 方向
     */
    public PopWindowManager(@Nullable Context context, @Nullable View view, @Nullable From from, int maxDp) {
        if (context == null || view == null || from == null) return;

        this.context = context;
        this.from = from;
        this.ui = new UICalculation(context);
        this.setContentView(view);

        // size
        if (from == From.TOP || from == From.BOTTOM) {
            this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            this.setHeight(ui.dp2px(maxDp));
        } else {
            this.setWidth(ui.dp2px(maxDp));
            this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        }

        // animation
        /*if (from == From.TOP) this.setAnimationStyle(R.style.Core_Pop_Top_Animation);
        else */if (from == From.BOTTOM) this.setAnimationStyle(R.style.Core_Pop_Bottom_Animation);
        else if (from == From.LEFT) this.setAnimationStyle(R.style.Core_Pop_Left_Animation);
        else if (from == From.RIGHT) this.setAnimationStyle(R.style.Core_Pop_Right_Animation);


        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // listener
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // 恢复屏幕背景透明度
                backgroundAlpha(1f);
            }
        });
    }

    public PopWindowManager(@Nullable Context context, @LayoutRes int id, @Nullable From from) {
        this(context, LayoutInflater.from(context).inflate(id, null, false), from, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setAnchorView(@LayoutRes int parentId) {
        setAnchorView(LayoutInflater.from(context).inflate(parentId, null, false));
    }

    public void setAnchorView(@Nullable View parentView) {
        this.parentView = parentView;
    }

    public void show() {
        if (parentView == null) {
            Log.e(TAG, "请先调用 setAnchorView() 设置锚点View");
            return;
        }

        try {

            if (from == From.TOP) this.showAsDropDown(parentView, 0, 0); // this.showAtLocation(parentView, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
            else if (from == From.BOTTOM) this.showAtLocation(parentView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            else if (from == From.LEFT) this.showAtLocation(parentView, Gravity.START, 0, 500);
            else if (from == From.RIGHT) this.showAtLocation(parentView, Gravity.END, 0, 500);

            // 设置屏幕背景透明度
            backgroundAlpha(0.5f);

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
            Log.e(TAG, "请不要在 Activity 启动时直接调用!");
        }
    }

    /**
     * 设置屏幕的透明背景
     */
    public void setAlphaBackgroud(@Nullable FragmentActivity activity) {
        this.activity = activity;
    }

    /**
     * 设置屏幕的背景透明度
     */
    protected void backgroundAlpha(float alpha) {
        if (activity == null) return;

        final WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha; // [0.0, 1.0]
        activity.getWindow().setAttributes(lp);
    }
}
