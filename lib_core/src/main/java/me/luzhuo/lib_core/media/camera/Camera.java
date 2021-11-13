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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import me.luzhuo.lib_core.app.appinfo.AppManager;
import me.luzhuo.lib_core.data.hashcode.HashManager;
import me.luzhuo.lib_core.media.ImageManager;
import me.luzhuo.lib_file.FileManager;

/**
 * 拍照后的结果处理
 * authority 在每个应用都必须是唯一的, 所以不同的应用是不一样的
 * authority 在代码中, 和 Manifest 这两处修改
 */
class Camera extends ActivityResultContract<Void, String> {
    private Uri imageUri;
    private File photoFile;
    private final FileManager fileManager = new FileManager();
    private final ImageManager imageManager = new ImageManager();
    private static final String authority = AppManager.AUTHORITY;
    private Context context;

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Void input) {
        this.context = context;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        photoFile = new File(fileManager.getCacheDirectory(context), HashManager.getInstance().getUuid());
        // 指定调用相机拍照后照片的储存路径
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) imageUri = FileProvider.getUriForFile(context, authority + context.getPackageName(), photoFile);
        else imageUri = Uri.fromFile(photoFile);

        // 调用系统照相机
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        return intent;
    }

    @Override
    public String parseResult(int resultCode, @Nullable Intent intent) {
        if (resultCode != Activity.RESULT_OK) return null;

        try {
            int degree = imageManager.getDegreeFromPhotoFile(photoFile.getAbsolutePath());
            if (degree == 0) return photoFile.getAbsolutePath();

            // 旋转并存储
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(imageUri));
            bitmap = imageManager.rotateBitmap(bitmap, degree);
            fileManager.Bitmap2JPGFile(bitmap, photoFile.getAbsolutePath());

            return photoFile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}