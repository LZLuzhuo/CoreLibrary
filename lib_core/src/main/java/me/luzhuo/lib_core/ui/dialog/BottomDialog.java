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

import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.luzhuo.lib_core.R;
import me.luzhuo.lib_core.ui.dialog.adapter.BottomMenuAdapter;

/**
 * Description: 从底部弹出的Dialog
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 17:32
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class BottomDialog {
    private BottomDialog(){}
    public static BottomDialog instance() {
        return Instance.instance;
    }
    private static class Instance {
        private static final BottomDialog instance = new BottomDialog();
    }

    /**
     * 显示自定义布局的 底部弹窗
     *
     * <p>
     * Example:
     * private BottomSheetDialog dialog;
     * public void OnClick(View _) {
     *      if(dialog != null) {
     *         dialog.show();
     *         return;
     *     }
     *
     *     View view = LayoutInflater.from(this).inflate(R.layout.bottom_dialog_fragment, null, false);
     *     dialog = BottomDialog.instance().show(this, view);
     * }
     * </p>
     *
     * @param context Context
     * @param view View
     */
    @MainThread
    public BottomSheetDialog show(@NonNull Context context, @NonNull View view) {
        if(Looper.myLooper() != Looper.getMainLooper()) throw new IllegalStateException("You must create it on the main thread.");

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, R.style.Core_BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        return bottomSheetDialog;
    }

    /**
     * 显示底部弹窗菜单
     *
     * <p>
     * Example:
     * private BottomSheetDialog dialog;
     * public void OnClick(View _) {
     *     if(dialog != null) {
     *         dialog.show();
     *         return;
     *     }
     *
     *     String[] menus = new String[]{"设置备注", "加入黑名单", "删除", "取消"};
     *     String[] colors = new String[]{"#111111", "#111111", "#111111", "#F24343"};
     *     dialog = BottomDialog.instance().showMenu(this, menus, colors, new BottomDialog.OnMenuItemClick() {
     *         @Override
     *         public void onItemClick(String[] menus, int position, String menu) {
     *             dialog.dismiss();
     *             Log.e(TAG, "" + Arrays.toString(menus) + " : " + position + " : " + menu);
     *         }
     *     });
     * }
     * </p>
     *
     * @param context Context
     * @param menus string menus
     * @param colors string colors
     * @param onMenuItemClick MenuAdapter.OnMenuItemClick
     * @return BottomSheetDialog
     */
    @MainThread
    public BottomSheetDialog showMenu(@NonNull Context context, @NonNull List<String> menus, @Nullable List<String> colors, @Nullable OnMenuItemClick onMenuItemClick) {
        // create view
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        BottomMenuAdapter adapter = new BottomMenuAdapter(R.layout.core_item_bottom_dialog_menu, menus, colors, onMenuItemClick);
        recyclerView.setAdapter(adapter);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundResource(R.drawable.core_bottom_dialog);
        linearLayout.removeAllViews();
        linearLayout.addView(recyclerView);

        // show dialog
        return show(context, linearLayout);
    }

    @MainThread
    public BottomSheetDialog showMenu(@NonNull Context context, @NonNull List<String> menus, @Nullable OnMenuItemClick onMenuItemClick) {
        return showMenu(context, menus, null, onMenuItemClick);
    }

    public interface OnMenuItemClick {
        public void onItemClick(List<String> menus, int position, String menu);
    }
}
