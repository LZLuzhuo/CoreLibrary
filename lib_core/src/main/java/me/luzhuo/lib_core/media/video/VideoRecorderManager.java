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
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import me.luzhuo.lib_core.ui.toast.ToastManager;

/**
 * Description: 录像
 * @Author: Luzhuo
 * @Creation Date: 2021/9/8 0:39
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class VideoRecorderManager {
    private static final String tag = "videoRecorderFragment";
    private IVideoRecorderCallback videoRecorderCallback;
    private FragmentActivity activity;
    // 默认品质为高, 低品质在某些手机上会很糊
    private VideoQuality quality = VideoQuality.High;
    private int durationLimit = Integer.MAX_VALUE;

    /**
     * 请在Activity的onCreate回调里创建该对象
     */
    public VideoRecorderManager(@NonNull FragmentActivity activity) {
        this.activity = activity;
    }

    /**
     * 请在Fragment的OnCreate回调里创建该对象
     */
    public VideoRecorderManager(@NonNull Fragment fragment) {
        this(fragment.requireActivity());
    }

    /**
     * 设置视频质量
     */
    public VideoRecorderManager setVideoQuality(@NonNull VideoQuality quality) {
        this.quality = quality;
        return this;
    }

    /**
     * 设置录制时长, 单位s
     */
    public VideoRecorderManager setVideoDurationLimit(int durationLimit) {
        this.durationLimit = durationLimit;
        return this;
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public void show() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ToastManager.show(activity, "请授予录像权限");
            return;
        }

        final FragmentManager fragmentManager = activity.getSupportFragmentManager();
        final Fragment exitedFragment = fragmentManager.findFragmentByTag(tag);

        final VideoFragment fragment;
        if (exitedFragment != null) fragment = (VideoFragment) exitedFragment;
        else {
            // 把新创建的Fragment添加到Activity中
            final VideoFragment invisibleFragment = VideoFragment.instance(quality, durationLimit);
            fragmentManager.beginTransaction().add(invisibleFragment, tag).commitNowAllowingStateLoss();
            fragment = invisibleFragment;
        }
        fragment.show(videoRecorderCallback);
    }

    @NonNull
    public VideoRecorderManager setVideoRecorderCallback(@NonNull IVideoRecorderCallback videoRecorderCallback) {
        this.videoRecorderCallback = videoRecorderCallback;
        return this;
    }
}
