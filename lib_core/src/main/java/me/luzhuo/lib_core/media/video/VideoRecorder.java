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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.core.util.Pair;
import me.luzhuo.lib_core.app.base.CoreBaseApplication;
import me.luzhuo.lib_core.data.hashcode.HashManager;
import me.luzhuo.lib_file.FileManager;

/**
 * Description: 录像结果的处理
 * @Author: Luzhuo
 * @Creation Date: 2021/9/8 0:44
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
class VideoRecorder extends ActivityResultContract<Void, Pair<Uri, File>> {
    private Uri videoUri;
    private File videoFile;
    private final FileManager fileManager = new FileManager(CoreBaseApplication.appContext);
    private static final String authority = "me.luzhuo.fileprovider.";
    private VideoQuality quality;
    private int durationLimit;

    /**
     * @param quality 视频录制质量
     * @param durationLimit 限制录制时长, 单位s
     */
    public VideoRecorder(@NonNull VideoQuality quality, int durationLimit) {
        this.quality = quality;
        this.durationLimit = durationLimit;
    }

    public VideoRecorder() {
        this.quality = VideoQuality.High;
        this.durationLimit = Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Void input) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        videoFile = new File(fileManager.getCacheDirectory(), HashManager.getInstance().getUuid());
        // 指定调用相机拍照后照片的储存路径
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) videoUri = FileProvider.getUriForFile(context, authority + context.getPackageName(), videoFile);
        else videoUri = Uri.fromFile(videoFile);

        // 调用系统照相机
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, durationLimit);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, quality.getQuality());
        return intent;
    }

    @Override
    public Pair<Uri, File> parseResult(int resultCode, @Nullable Intent intent) {
        if (resultCode != Activity.RESULT_OK) return null;

        try {
            Uri videoUri = intent.getData();
            return new Pair<>(videoUri, videoFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
