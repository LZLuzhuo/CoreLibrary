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
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import me.luzhuo.lib_core.ui.toast.ToastManager;

/**
 * 拍照
 */
public class CameraManager {
    private FragmentActivity activity;
    private ActivityResultLauncher<Context> takePicture;
    private ICameraCallback cameraCallback;

    public CameraManager(FragmentActivity activity) {
        this.activity = activity;

        takePicture = activity.registerForActivityResult(new Camera(), new ActivityResultCallback<String>() {
            @Override
            public void onActivityResult(String result) {
                if(cameraCallback != null && !TextUtils.isEmpty(result)) cameraCallback.onCameraCallback(result);
            }
        });
    }

    public void show() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ToastManager.show(activity, "请授予拍照权限");
            return;
        }
        takePicture.launch(activity);
    }

    public CameraManager setCameraCallback(ICameraCallback cameraCallback) {
        this.cameraCallback = cameraCallback;
        return this;
    }
}
