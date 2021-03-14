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
import android.view.View;
import android.widget.Toast;

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
    private static Handler mainThread = new Handler(Looper.getMainLooper());
    private static Toast quickToast;

    /**
     * 默认的吐司显示方式
     */
    public static void show(final Context context, final String content){
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    show(context, content);
                }
            });
            return;
        }

        Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 快速显示的吐司;
     * BUG: 如果连续不断的执行, 会导致一段时间后的吐司不显示
     */
    @Deprecated
    public static void showQuick(final Context context, final String content) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    showQuick(context, content);
                }
            });
            return;
        }

        if(quickToast == null) quickToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        quickToast.setText(content);
        quickToast.show();
    }

    /**
     * 显示View的吐司
     * toast.showViewToast(this, LayoutInflater.from(this).inflate(R.layout.view_toast, null, false))
     */
    public static void showView(final Context context, final View view) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    showView(context, view);
                }
            });
            return;
        }

        Toast viewToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        viewToast.setView(view);
        viewToast.show();
    }
}