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
package me.luzhuo.lib_core.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

import me.luzhuo.lib_file.FileManager;

import static android.media.ThumbnailUtils.OPTIONS_RECYCLE_INPUT;

/**
 * Description: 缩率图管理
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/6/2 16:54
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class ThumbnailManager {

    /**
     * 从Video中获取缩略图
     * @param videoFilePath video path or video uri
     * @return Bitmap or null
     */
    public Bitmap getVideoThumbnail(Context context, String videoFilePath) {
        FileManager fileManager = new FileManager(context);
        if (context == null || TextUtils.isEmpty(videoFilePath) || !fileManager.exists(videoFilePath)) return null;

        MediaMetadataRetriever media = new MediaMetadataRetriever();
        if (fileManager.isUriForFile(videoFilePath)) media.setDataSource(context, Uri.parse(videoFilePath));
        else media.setDataSource(videoFilePath);
        return media.getFrameAtTime();
    }

    public Bitmap getVideoThumbnail(Context context, Uri videoFileUri) {
        return getVideoThumbnail(context, videoFileUri.toString());
    }

    /**
     * 从Video中获取忽略图, 并保存到指定路径
     * get primary image file from video
     * @param videoFile video path or video uri
     * @return primary image file. or null
     */
    public String getVideoThumbnail(Context context, String videoFile, String savePath) {
        try {
            Bitmap bitmap = getVideoThumbnail(context, videoFile);
            if(bitmap == null) return null;

            new FileManager().Bitmap2JPGFile(bitmap, savePath);
            return savePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getVideoThumbnail(Context context, Uri videoFileUri, String savePath) {
        return getVideoThumbnail(context, videoFileUri.toString(), savePath);
    }

    /**
     * 从Image中获取缩略图
     *
     * Note: 仅支持从filePath中获取
     *
     * @param imageFilePath image file path
     * @param width targeted width
     * @param height targeted height
     * @return This value may be null
     */
    public Bitmap getImageThumbnail(String imageFilePath, int width, int height){
        if(imageFilePath == null || imageFilePath.isEmpty() || !new File(imageFilePath).exists()) return null;

        Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
        return ThumbnailUtils.extractThumbnail(bitmap, width, height, OPTIONS_RECYCLE_INPUT);
    }

    /**
     * 从 Image resource id 中获取图片缩略图
     * @param context Context
     * @param resId image res id
     * @param width targeted width
     * @param height targeted height
     * @return This value may be null
     */
    public Bitmap getImageThumbnail(Context context, int resId, int width, int height){
        if(resId == 0) return null;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        return ThumbnailUtils.extractThumbnail(bitmap, width, height, OPTIONS_RECYCLE_INPUT);
    }

    /**
     * get thumbnail for bitmap
     * @param bitmap Bitmap
     * @param width targeted width
     * @param height targeted height
     * @return This value may be null
     */
    public Bitmap getImageThumbnail(Bitmap bitmap, int width, int height){
        if(bitmap == null) return null;

        return ThumbnailUtils.extractThumbnail(bitmap, width, height, OPTIONS_RECYCLE_INPUT);
    }
}
