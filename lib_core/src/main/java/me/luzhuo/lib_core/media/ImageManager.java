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
import android.media.ExifInterface;

import java.io.IOException;

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
    public Bitmap rotateIfRequired(Bitmap bitmap, String photoPath) {
        int degree = getDegreeFromPhotoFile(photoPath);
        if (degree == 0) return bitmap;

        return rotateBitmap(bitmap, degree);
    }

    /**
     * 获取照片文件的旋转角度
     * @param photoPath 照片我恩建路径
     * @return 旋转角度, 0表示未旋转
     */
    public int getDegreeFromPhotoFile(String photoPath) {
        try {
            ExifInterface exifInterface = new ExifInterface(photoPath);
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
        }
    }

    /**
     * 按指定的角度旋转图片
     * @param bitmap 待旋转的图片
     * @param degree 旋转的角度
     * @return 旋转之后的图片
     */
    public Bitmap rotateBitmap(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle(); // 将不再需要的bitmap对象回收
        return rotatedBitmap;
    }
}
