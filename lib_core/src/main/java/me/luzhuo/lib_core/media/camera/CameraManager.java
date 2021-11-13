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
package me.luzhuo.lib_core.media.camera;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import me.luzhuo.lib_core.ui.toast.ToastManager;

/**
 * 拍照
 */
public class CameraManager {
    private static final String tag = "cameraFragment";
    private ICameraCallback cameraCallback;
    private FragmentActivity activity;

    /**
     * 请在Activity的onCreate回调里创建该对象
     */
    public CameraManager(FragmentActivity activity) {
        this.activity = activity;
    }

    /**
     * 请在Fragment的OnCreate回调里创建该对象
     */
    public CameraManager(Fragment fragment) {
        this(fragment.requireActivity());
    }

    public void show() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ToastManager.show(activity, "请授予拍照权限");
            return;
        }

        final FragmentManager fragmentManager = activity.getSupportFragmentManager();
        final Fragment exitedFragment = fragmentManager.findFragmentByTag(tag);

        final CameraFragment fragment;
        if (exitedFragment != null) fragment = (CameraFragment) exitedFragment;
        else {
            // 把新创建的Fragment添加到Activity中
            final CameraFragment invisibleFragment = new CameraFragment();
            fragmentManager.beginTransaction().add(invisibleFragment, tag).commitNowAllowingStateLoss();
            fragment = invisibleFragment;
        }
        fragment.show(cameraCallback);
    }

    public CameraManager setCameraCallback(ICameraCallback cameraCallback) {
        this.cameraCallback = cameraCallback;
        return this;
    }
}
