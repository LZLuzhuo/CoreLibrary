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
package me.luzhuo.lib_core.app.keyboard;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description: 软键盘工具
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 15:14
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class KeyBoardManager {
    private static KeyBoardManager instance;
    private InputMethodManager manager;
    private final static int flags = 0;

    private KeyBoardManager(@NonNull Context context) {
        manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static KeyBoardManager getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (KeyBoardManager.class) {
                if (instance == null) instance = new KeyBoardManager(context.getApplicationContext());
            }
        }
        return instance;
    }

    /**
     * 显示键盘
     * 弹出的键盘类型与editText的配置有关
     */
    public void show(@Nullable EditText editText) {
        if (editText == null) return;

        editText.requestFocus(); // 请求获取焦点
        manager.showSoftInput(editText, flags);
    }

    /**
     * 隐藏软键盘
     */
    public void hide(@Nullable View view) {
        if (view == null) return;

        try {
            manager.hideSoftInputFromWindow(view.getWindowToken(), flags);
        } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * 隐藏软键盘
     */
    public void hide(@Nullable Activity activity) {
        if (activity == null) return;

        try {
            // java.lang.NullPointerException: Attempt to invoke virtual method 'android.os.IBinder android.view.View.getWindowToken()' on a null object reference
            manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), flags);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
