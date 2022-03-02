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

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.exifinterface.media.ExifInterface;
import me.luzhuo.lib_core.app.base.CoreBaseApplication;

/**
 * Description: 图片媒体管理类
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/12/30 15:55
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class ImageManager {

    /**
     * 自动矫正图片的角度
     * 用于矫正有些相机拍摄的图片是左右相反的, 或者是旋转的
     * @param bitmap Bitmap照片对象
     * @param photoPath Bitmap对应的本地文件
     */
    public Bitmap rotateIfRequired(@NonNull Bitmap bitmap, @NonNull String photoPath) {
        int degree = getDegreeFromPhotoFile(photoPath);
        if (degree == 0) return bitmap;

        return rotateBitmap(bitmap, degree);
    }

    public Bitmap rotateIfRequired(@NonNull Bitmap bitmap, @NonNull InputStream photoPath) {
        int degree = getDegreeFromPhotoFile(photoPath);
        if (degree == 0) return bitmap;

        return rotateBitmap(bitmap, degree);
    }

    /**
     * 获取照片文件的旋转角度
     * @param photoPathOrUri 照片本地路径, 或Uri路径
     * @return 旋转角度, 0表示未旋转
     */
    public int getDegreeFromPhotoFile(@NonNull String photoPathOrUri) {
        try {
            if (photoPathOrUri.startsWith("content://")) {
                return getDegreeFromPhotoFile(CoreBaseApplication.appContext.getContentResolver().openInputStream(Uri.parse(photoPathOrUri)));
            } else {
                return getDegreeFromPhotoFile(new FileInputStream(new File(photoPathOrUri)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getDegreeFromPhotoFile(@NonNull Uri photoUri) {
        return getDegreeFromPhotoFile(photoUri.toString());
    }

    public int getDegreeFromPhotoFile(@NonNull InputStream inputStream) {
        try {
            ExifInterface exifInterface = new ExifInterface(inputStream);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90: return 90;
                case ExifInterface.ORIENTATION_ROTATE_180: return 180;
                case ExifInterface.ORIENTATION_ROTATE_270: return 270;
                default: return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } finally {
            try { inputStream.close(); } catch (IOException ioException) { ioException.printStackTrace(); }
        }
    }

    /**
     * 按指定的角度旋转图片
     * @param bitmap 待旋转的图片
     * @param degree 旋转的角度
     * @param recycler 是否对原Bitmap进行回收
     * @return 旋转之后的图片
     */
    @NonNull
    public Bitmap rotateBitmap(@NonNull Bitmap bitmap, int degree, final boolean recycler) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (recycler) bitmap.recycle(); // 将不再需要的bitmap对象回收
        return rotatedBitmap;
    }

    @NonNull
    public Bitmap rotateBitmap(@NonNull Bitmap bitmap, int degree) {
        return rotateBitmap(bitmap, degree, true);
    }
}
