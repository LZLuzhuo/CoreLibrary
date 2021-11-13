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
package me.luzhuo.lib_core.media.camera;

import android.text.TextUtils;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

/**
 * Description: 实现原理见 权限请求库
 * @Author: Luzhuo
 * @Creation Date: 2021/10/20 21:29
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class CameraFragment extends Fragment {
    private ICameraCallback cameraCallback;

    private ActivityResultLauncher<Void> takePicture = registerForActivityResult(new Camera(), new ActivityResultCallback<String>() {
        @Override
        public void onActivityResult(String result) {
            if(cameraCallback != null && !TextUtils.isEmpty(result)) cameraCallback.onCameraCallback(result);
        }
    });

    public void show(ICameraCallback cameraCallback) {
        this.cameraCallback = cameraCallback;
        takePicture.launch(null);
    }
}
