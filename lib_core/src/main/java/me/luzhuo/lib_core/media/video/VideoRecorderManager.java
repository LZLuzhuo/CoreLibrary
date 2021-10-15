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
package me.luzhuo.lib_core.media.video;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.File;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import me.luzhuo.lib_core.ui.toast.ToastManager;

/**
 * Description: 录像
 * @Author: Luzhuo
 * @Creation Date: 2021/9/8 0:39
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class VideoRecorderManager {
    private Context context;
    private ActivityResultLauncher<Void> takeVideo;
    private IVideoRecorderCallback videoRecorderCallback;

    /**
     * 请在Activity的onCreate回调里创建该对象
     */
    public VideoRecorderManager(FragmentActivity activity) {
        this.context = activity;

        takeVideo = activity.registerForActivityResult(new VideoRecorder(), new ActivityResultCallback<Pair<Uri, File>>() {
            @Override
            public void onActivityResult(Pair<Uri, File> result) {
                if(videoRecorderCallback != null && result != null) videoRecorderCallback.onVideoRecorderCallback(result.first, result.second);
            }
        });
    }

    /**
     * 请在Fragment的OnCreate回调里创建该对象
     */
    public VideoRecorderManager(Fragment fragment) {
        this.context = fragment.getContext();

        takeVideo = fragment.registerForActivityResult(new VideoRecorder(), new ActivityResultCallback<Pair<Uri, File>>() {
            @Override
            public void onActivityResult(Pair<Uri, File> result) {
                if(videoRecorderCallback != null && result != null) videoRecorderCallback.onVideoRecorderCallback(result.first, result.second);
            }
        });
    }

    public void show() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ToastManager.show(context, "请授予录像权限");
            return;
        }
        takeVideo.launch(null);
    }

    public VideoRecorderManager setCameraCallback(IVideoRecorderCallback videoRecorderCallback) {
        this.videoRecorderCallback = videoRecorderCallback;
        return this;
    }
}
