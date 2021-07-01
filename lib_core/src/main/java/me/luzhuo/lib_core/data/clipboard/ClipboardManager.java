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
package me.luzhuo.lib_core.data.clipboard;

import android.content.ClipData;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Description: 剪贴板管理
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 15:17
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class ClipboardManager implements LifecycleObserver {
    private android.content.ClipboardManager clipboardManager;
    private android.content.ClipboardManager.OnPrimaryClipChangedListener clipChangedListener;

    /**
     * 如果需要监听剪贴板, 请使用 ClipboardManager(AppCompatActivity) 或 ClipboardManager(Fragment) 构造函数.
     */
    public ClipboardManager(Context context) {
        clipboardManager = (android.content.ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
    }

    public ClipboardManager(FragmentActivity activity) {
        this(activity.getApplicationContext());
        activity.getLifecycle().addObserver(this);
    }

    public ClipboardManager(Fragment fragment) {
        this(fragment.requireContext());
        fragment.getLifecycle().addObserver(this);
    }

    /**
     * 拷贝内容到剪贴板
     * @param content String
     */
    public void copy(CharSequence content) {
        ClipData clipData = ClipData.newPlainText("copy", content);
        clipboardManager.setPrimaryClip(clipData);
    }

    /**
     * 从剪贴板获取内容
     */
    public CharSequence getContent() {
        if (clipboardManager.hasPrimaryClip() && clipboardManager.getPrimaryClip().getItemCount() > 0) {
            return clipboardManager.getPrimaryClip().getItemAt(0).getText();
        } else return null;
    }

    /**
     * 添加剪贴事件监听
     * @param clipChangedListener 剪贴版监听接口
     */
    public void setClipListener(android.content.ClipboardManager.OnPrimaryClipChangedListener clipChangedListener) {
        this.clipChangedListener = clipChangedListener;
        clipboardManager.addPrimaryClipChangedListener(clipChangedListener);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected void onDestroy() {
        if (clipChangedListener == null) return;
        clipboardManager.removePrimaryClipChangedListener(clipChangedListener);
        clipChangedListener = null;
    }
}
