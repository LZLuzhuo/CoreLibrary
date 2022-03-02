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
package me.luzhuo.lib_core.media.audio;

import android.content.Context;
import android.net.Uri;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * Description: 录音
 * @Author: Luzhuo
 * @Creation Date: 2021/9/8 1:07
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class AudioManager {
    private Context context;
    private ActivityResultLauncher<Void> takeAudio;
    private IAudioCallback audioCallback;

    /**
     * 请在Activity的onCreate回调里创建该对象
     */
    public AudioManager(@NonNull FragmentActivity activity) {
        this.context = activity;

        takeAudio = activity.registerForActivityResult(new Audio(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(audioCallback != null && result != null) audioCallback.onAudioCallback(result);
            }
        });
    }

    /**
     * 请在Fragment的OnCreate回调里创建该对象
     */
    public AudioManager(@NonNull Fragment fragment) {
        this.context = fragment.getContext();

        takeAudio = fragment.registerForActivityResult(new Audio(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(@Nullable Uri result) {
                if(audioCallback != null && result != null) audioCallback.onAudioCallback(result);
            }
        });
    }

    public void show() {
        takeAudio.launch(null);
    }

    public AudioManager setAudioCallback(IAudioCallback audioCallback) {
        this.audioCallback = audioCallback;
        return this;
    }
}
