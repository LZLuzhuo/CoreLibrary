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

import android.net.Uri;
import android.os.Bundle;

import java.io.File;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

/**
 * Description: 实现原理见 权限请求库
 * @Author: Luzhuo
 * @Creation Date: 2021/10/20 21:39
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
@RestrictTo(LIBRARY)
public class VideoFragment extends Fragment {
    private IVideoRecorderCallback videoRecorderCallback;
    private ActivityResultLauncher<Void> takeVideo;

    private VideoFragment() { }

    @RestrictTo(LIBRARY)
    public static VideoFragment instance(@NonNull VideoQuality quality, int durationLimit) {
        final VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putSerializable("quality", quality);
        args.putInt("durationLimit", durationLimit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) return;

        final VideoQuality quality = (VideoQuality) getArguments().getSerializable("quality");
        int durationLimit = getArguments().getInt("durationLimit");

        takeVideo = registerForActivityResult(new VideoRecorder(quality, durationLimit), new ActivityResultCallback<Pair<Uri, File>>() {
            @Override
            public void onActivityResult(Pair<Uri, File> result) {
                if(videoRecorderCallback != null && result != null) videoRecorderCallback.onVideoRecorderCallback(result.first, result.second);
            }
        });
    }

    @RestrictTo(LIBRARY)
    public void show(@Nullable IVideoRecorderCallback videoRecorderCallback) {
        this.videoRecorderCallback = videoRecorderCallback;
        takeVideo.launch(null);
    }
}
