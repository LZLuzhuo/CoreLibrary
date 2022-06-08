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
package me.luzhuo.lib_core.ui.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_core.ui.calculation.UICalculation;
import me.luzhuo.lib_core.ui.dialog.adapter.DialogMenuAdapter;
import me.luzhuo.lib_core.ui.widget.rightmenu.OnMenuCallback;

/**
 * Description: 弹窗
 * 子线程更新ui安全
 * 所有没有返回值的函数, 线程都是安全的;
 * 所有有返回值的函数, 线程都是不安全的.
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 17:33
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class Dialog {
    private Dialog(){}
    public static Dialog instance(){
        return Instance.instance;
    }
    private static class Instance{
        private static final Dialog instance = new Dialog();
    }

    public interface OnClickListener {
        public void onOk(Object data);
        public void onCancel(Object data);
    }

    public interface OnSingleChoice {
        public void onOk(int witch, String item, String[] items, Object post);
        public void onCancel(int witch, String[] items, Object post);
    }

    public interface OnMultiChoice {
        public void onOk(int witch, String[] items, boolean[] checkeds, Object post);
        public void onCancel(int witch, String[] items, boolean[] checkeds, Object post);
        public void onItem(int witch, String item, boolean isChecked, String[] items, boolean[] checkeds, Object post);
    }

    /**
     * default dialog
     * @param context Activity Context
     * @param title title
     * @param content content
     * @param okName ok button name
     * @param cancelMame cancel button name
     * @param isCancelable whether it can be cancelled, return or click outside the control.
     * @param listener OnClickListener or null
     * @param post String int bean or null
     */
    public AlertDialog build(@NonNull final Context context, @Nullable final String title, @Nullable final String content, @Nullable final String okName, @Nullable final String cancelMame, final boolean isCancelable, @Nullable final OnClickListener listener, @Nullable final Object post){
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("You must create it on the main thread.");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(isCancelable);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // 监听取消(返回/点击控件外 取消)
                if (listener != null) listener.onCancel(post);
            }});
        if (!TextUtils.isEmpty(okName)) builder.setPositiveButton(okName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点了确定按钮
                if (listener != null) listener.onOk(post);
            }});
        if (!TextUtils.isEmpty(cancelMame)) builder.setNegativeButton(cancelMame, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点了取消按钮
                if (listener != null) listener.onCancel(post);
            }});
        return builder.create();
    }

    public AlertDialog show(@NonNull final Context context, @Nullable final String title, @Nullable final String content, @Nullable final String okName, @Nullable final String cancelMame, final boolean isCancelable, @Nullable final OnClickListener listener, @Nullable final Object post){
        AlertDialog build = build(context, title, content, okName, cancelMame, isCancelable, listener, post);
        build.show();
        return build;
    }

    /**
     * 单选的Dialog
     * @param context Activity Context
     * @param title title
     * @param items String[]
     * @param checkedItem no select -1
     * @param isCancelable whether it can be cancelled, return or click outside the control.
     * @param listener OnSingleChoice
     * @param post String int bean or null
     */
    public AlertDialog build(@NonNull final Context context, @Nullable final String title, @NonNull final String[] items, final int checkedItem, final boolean isCancelable, @Nullable final OnSingleChoice listener, @Nullable final Object post){
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("You must create it on the main thread.");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(isCancelable);
        builder.setTitle(title);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // 监听取消(返回/点击控件外 取消)
                if (listener != null) listener.onCancel(-1, items, post);
            }});
        builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String item = items[which];
                dialog.dismiss();

                if (listener != null) listener.onOk(which, item, items, post);
            }});
        return builder.create();
    }

    public AlertDialog show(@NonNull final Context context, @Nullable final String title, @NonNull final String[] items, final int checkedItem, final boolean isCancelable, @Nullable final OnSingleChoice listener, @Nullable final Object post){
        AlertDialog build = build(context, title, items, checkedItem, isCancelable, listener, post);
        build.show();
        return build;
    }

    /**
     * 多选的Dialog
     * @param context Context
     * @param title title
     * @param items String[]
     * @param checkeds boolean[]
     * @param okName ok button name
     * @param isCancelable whether it can be cancelled, return or click outside the control.
     * @param listener OnMultiChoice
     * @param post String int bean or null
     */
    public AlertDialog build(@NonNull final Context context, @Nullable final String title, @NonNull final String[] items, @NonNull final boolean[] checkeds, @NonNull final String okName, final boolean isCancelable, @Nullable final OnMultiChoice listener, @Nullable final Object post){
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("You must create it on the main thread.");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(isCancelable);
        builder.setTitle(title);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // 监听取消(返回/点击控件外 取消)
                if (listener != null) listener.onCancel(-1, items, checkeds, post);
            }});
        builder.setMultiChoiceItems(items, checkeds, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                String item = items[which];
                if (listener != null) listener.onItem(which, item, isChecked, items, checkeds, post);
            }});
        builder.setPositiveButton(okName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) listener.onOk(which, items, checkeds, post);
            }});
        return builder.create();
    }

    public AlertDialog show(@NonNull final Context context, @Nullable final String title, @NonNull final String[] items, @NonNull final boolean[] checkeds, @NonNull final String okName, final boolean isCancelable, @Nullable final OnMultiChoice listener, @Nullable final Object post){
        AlertDialog build = build(context, title, items, checkeds, okName, isCancelable, listener, post);
        build.show();
        return build;
    }

    /**
     * 进度条的Dialog
     * 必须在主线程创建Dialog, 然后在子线程更新进度条
     *
     * <p>
     * Example:
     * // 主线程创建
     * final ProgressDialog progressDialog = Dialog.instance().show(MainActivity.this, "进度条", 100);
     *
     * // 子线程更新
     * progressDialog.setProgress(current);
     *
     * // 关闭dialog
     * progressDialog.dismiss();
     * </p>
     *
     * @param context Context
     * @param title title
     * @param max max progress
     * @return ProgressDialog
     */
    public ProgressDialog build(@NonNull Context context, @Nullable String title, @Nullable final String content, final boolean isCancelable, int max) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("You must create it on the main thread.");
        }

        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(isCancelable);
        dialog.setTitle(title);
        dialog.setMessage(content);
        dialog.setMax(max);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        return dialog;
    }

    public ProgressDialog show(@NonNull Context context, @Nullable String title, @Nullable final String content, final boolean isCancelable, int max) {
        ProgressDialog build = build(context, title, content, isCancelable, max);
        build.show();
        return build;
    }

    /**
     * 自定义View的Dialog
     *
     * <p>
     * Example1:
     * View view = LayoutInflater.from(this).inflate(R.layout.dialog_view, null, false);
     *
     * Example2:
     * ImageView view = new ImageView(this);
     * view.setImageResource(R.mipmap.ic_launcher);
     * </p>
     *
     * @param context Context
     * @param title title
     * @param view View
     * @param okName ok button name
     * @param cancelMame cancel name
     * @param isCancelable whether it can be cancelled, return or click outside the control.
     * @param listener OnClickListener
     * @param post String int bean or null
     */
    public AlertDialog build(@NonNull final Context context, @Nullable final String title, @NonNull final View view, @Nullable final String okName, @Nullable final String cancelMame, final boolean isCancelable, @Nullable final OnClickListener listener, @Nullable final Object post){
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("You must create it on the main thread.");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(isCancelable);
        builder.setTitle(title);
        builder.setView(view);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // 监听取消(返回/点击控件外 取消)
                if (listener != null) listener.onCancel(post);
            }});
        if (!TextUtils.isEmpty(okName)) builder.setPositiveButton(okName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点了确定按钮
                if (listener != null) listener.onOk(post);
            }});
        if (!TextUtils.isEmpty(cancelMame)) builder.setNegativeButton(cancelMame, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 点了取消按钮
                if (listener != null) listener.onCancel(post);
            }});
        return builder.create();
    }

    public AlertDialog show(@NonNull final Context context, @Nullable final String title, @NonNull final View view, @Nullable final String okName, @Nullable final String cancelMame, final boolean isCancelable, @Nullable final OnClickListener listener, @Nullable final Object post){
        AlertDialog build = build(context, title, view, okName, cancelMame, isCancelable, listener, post);
        build.show();
        return build;
    }

    /**
     * 构建自定义布局Dialog
     *
     * <p>
     * Example1:
     * View view = LayoutInflater.from(this).inflate(R.layout.dialog_view, null, false);
     *
     * Example2:
     * ImageView view = new ImageView(this);
     * view.setImageResource(R.mipmap.ic_launcher);
     * </p>
     *
     * @param context Context
     * @param view View
     * @param isCancelable whether it can be cancelled, return or click outside the control.
     * @return AlertDialog
     *
     * @see #build(Context, String, View, String, String, boolean, OnClickListener, Object)
     */
    public AlertDialog build(@NonNull Context context, @StyleRes int style, @NonNull View view, boolean isCancelable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("You must create it on the main thread.");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context, style);
        builder.setCancelable(isCancelable);
        AlertDialog dialog = builder.create();
        dialog.setView(view, 0, 0, 0, 0);
        return dialog;
    }

    public AlertDialog build(@NonNull Context context, @NonNull View view, boolean isCancelable) {
        return build(context, 0, view, isCancelable);
    }

    public AlertDialog show(@NonNull Context context, @StyleRes int style, @NonNull View view, boolean isCancelable) {
        AlertDialog build = build(context, style, view, isCancelable);
        build.show();
        return build;
    }

    public AlertDialog show(@NonNull Context context, @NonNull View view, boolean isCancelable) {
        AlertDialog build = build(context, view, isCancelable);
        build.show();
        return build;
    }

    /**
     * 显示菜单形式的Dialog
     * @param context Context
     * @param menus String[] menus = new String[]{"菜单1", "菜单2", "菜单3", "菜单4", "菜单5"};
     * @param listener OnSingleChoice
     * @param post Object
     */
    public AlertDialog buildMenu(@NonNull final Context context, @NonNull final String[] menus, @Nullable final Dialog.OnSingleChoice listener, @Nullable final Object post) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("You must create it on the main thread.");
        }

        final AlertDialog builder = new AlertDialog.Builder(context).create();
        builder.setCancelable(true);

        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(new DialogMenuAdapter(menus, new OnMenuCallback() {
            @Override
            public void onMenu(int position, String menu) {
                builder.dismiss();
                if (listener != null) listener.onOk(position, menu, menus, post);
            }
        }));
        builder.setView(recyclerView);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (listener != null) listener.onCancel(-1, menus, post);
            }
        });
        return builder;
    }

    public AlertDialog showMenu(@NonNull final Context context, @NonNull final String[] menus, @Nullable final Dialog.OnSingleChoice listener, @Nullable final Object post) {
        AlertDialog build = buildMenu(context, menus, listener, post);
        return show(build, 0.5f, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 显示 AlertDialog, 并设定其宽高
     * @param dialog AlertDialog
     * @param width 宽度
     *              10dp, 实际宽度
     *              {@link WindowManager.LayoutParams#MATCH_PARENT} 或 -1, 宽度占满
     *              {@link WindowManager.LayoutParams#WRAP_CONTENT} 或 -2, 宽度包裹内容
     * @param height 高度
     */
    public static AlertDialog show(@NonNull AlertDialog dialog, int width, int height) {
        dialog.show();
        dialog.getWindow().setLayout(width, height);
        return dialog;
    }

    public static AlertDialog show(@NonNull AlertDialog dialog, @FloatRange(from = 0.0, to = 1.0) float widthPercent, @FloatRange(from = 0.0, to = 1.0) float heightPercent) {
        int[] display = new UICalculation(dialog.getContext()).getDisplay();
        return show(dialog, (int) (widthPercent * display[0]), (int) (heightPercent * display[1]));
    }

    public static AlertDialog show(@NonNull AlertDialog dialog, int width, @FloatRange(from = 0.0, to = 1.0) float heightPercent) {
        int[] display = new UICalculation(dialog.getContext()).getDisplay();
        return show(dialog, width, (int) (heightPercent * display[1]));
    }

    public static AlertDialog show(@NonNull AlertDialog dialog, @FloatRange(from = 0.0, to = 1.0) float widthPercent, int width) {
        int[] display = new UICalculation(dialog.getContext()).getDisplay();
        return show(dialog, (int) (widthPercent * display[0]), width);
    }

    /**
     * 默认的 确定 和 取消 点击后会自动关闭Dialog,
     * 如果不想自动关闭, 可以使用该方式显示; 即使Dialog已显示, 也可调用该函数
     * @param dialog AlterDialog
     * @param okClick 如果为null, 则系统默认方式; 如果不为null, 则覆盖系统默认方式
     * @param cancelClick 如果为null, 则系统默认方式; 如果不为null, 则覆盖系统默认方式
     */
    public static void show(AlertDialog dialog, View.OnClickListener okClick, View.OnClickListener cancelClick) {
        dialog.show();
        if (okClick != null) dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(okClick);
        if (cancelClick != null) dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(cancelClick);
    }
}
