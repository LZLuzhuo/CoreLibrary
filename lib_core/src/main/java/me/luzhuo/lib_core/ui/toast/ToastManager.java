/* Copyright 2015 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_core.ui.toast;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import me.luzhuo.lib_core.app.base.CoreBaseApplication;
import me.luzhuo.lib_core.ui.calculation.UICalculation;

/**
 * =================================================
 *
 * 作者:卢卓
 *
 * 版本:2.0
 *
 * 创建日期:2015-11-24 下午11:50:14
 *
 * 描述:Toast工具
 *
 * 修订历史: 1. 子线程更新ui安全
 *
 *
 * =================================================
 **/
public class ToastManager {
    private static final Handler mainThread = new Handler(Looper.getMainLooper());
    private static Toast quickToast;

    /**
     * 默认的吐司显示方式
     */
    public static void show(@NonNull Context context, @NonNull final String content) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    show(content);
                }
            });
            return;
        }

        Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

    public static void show(@NonNull final String content){
        show(CoreBaseApplication.appContext, content);
    }

    private static int toastHeight = 0;
    /**
     * 显示在 2/3 处的吐司
     * Android 11 (API30) 及以上不再允许自定义Toast的位置
     */
    @Deprecated
    public static void show2(@NonNull Context context, @NonNull final String content) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    show2(content);
                }
            });
            return;
        }

        if(toastHeight == 0) toastHeight = new UICalculation().getDisplay()[1] / 3;
        Toast toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, toastHeight);
        toast.show();
    }

    public static void show2(@NonNull final String content) {
        show2(CoreBaseApplication.appContext, content);
    }

    /**
     * 快速显示的吐司;
     * BUG: 如果连续不断的执行, 会导致一段时间后的吐司不显示
     */
    @Deprecated
    public static void showQuick(@NonNull Context context, @NonNull final String content) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    showQuick(content);
                }
            });
            return;
        }

        if(quickToast == null) quickToast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
        quickToast.setText(content);
        quickToast.show();
    }

    public static void showQuick(@NonNull final String content) {
        showQuick(CoreBaseApplication.appContext, content);
    }

    /**
     * 显示View的吐司
     * toast.showViewToast(this, LayoutInflater.from(this).inflate(R.layout.view_toast, null, false))
     * Android 11 (API30) 及以上, 后台不再允许使用自定义Toast, 前台不受影响
     */
    public static void showView(@NonNull Context context, @NonNull final View view) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    showView(view);
                }
            });
            return;
        }

        Toast viewToast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_SHORT);
        viewToast.setView(view);
        viewToast.show();
    }

    public static void showView(@NonNull final View view) {
        showView(CoreBaseApplication.appContext, view);
    }
}