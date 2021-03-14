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
package me.luzhuo.lib_core.data.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Description: 文件管理
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/7/29 22:42
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class FileManager {

    /**
     * 从 输入流 写入到文件
     * @param stream inputStream
     * @param filePath  local file path
     * @throws IOException
     */
    public void Stream2File(InputStream stream, String filePath) throws IOException {

        File localFile = new File(filePath);
        if(!localFile.getParentFile().exists()) localFile.getParentFile().mkdirs();
        if(!localFile.exists()) localFile.createNewFile();

        BufferedInputStream bis = new BufferedInputStream(stream);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(localFile));

        byte[] bys = new byte[1024 * 10];
        int len = 0;
        while((len = bis.read(bys)) != -1){
            bos.write(bys,0, len);
            bos.flush();
        }
        bis.close();
        bos.close();
    }

    /**
     * 该方式将图片存到本地, 大小将是原来图片的十几倍
     * @param bitmap Bitmap
     * @param filePath save file path
     * @throws IOException
     */
    public void Bitmap2PNGFile(Bitmap bitmap, String filePath) throws IOException {
        File localFile = new File(filePath);
        if (!localFile.getParentFile().exists()) localFile.getParentFile().mkdirs();
        if (!localFile.exists()) localFile.createNewFile();

        FileOutputStream fos = new FileOutputStream(localFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
    }

    /**
     * 该方式将图片存到本地, 大小与原来图片差不多大
     * @param bitmap Bitmap
     * @param filePath save file path
     * @throws IOException
     */
    public void Bitmap2JPGFile(Bitmap bitmap, String filePath) throws IOException {
        File localFile = new File(filePath);
        if (!localFile.getParentFile().exists()) {
            localFile.getParentFile().mkdirs();
        }

        if (!localFile.exists()) {
            localFile.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(localFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fos);
        fos.flush();
        fos.close();
    }

    /**
     * 检查文件地址, 用于适配 Android10+ 的分区存储.
     * 注意: 该方法是同步的.
     * 1. 如果是普通的文件路径, 则则返回文件路径
     * 2. 如果是content类型的uri路径, 则先缓存到外置cache, 然后再返回cache路径
     * @param path 文件路径, 可以为filepath, 也可以为content
     * @return 检测后的文件路径
     */
    public String checkFilePath(Context context, String path) {
        if(TextUtils.isEmpty(path)) return null;

        if (path.startsWith("content")) {
            try {
                Uri pathUri = Uri.parse(path);
                String savePath = context.getExternalCacheDir() + File.separator + UUID.randomUUID().toString().replace("-", "");
                InputStream inputStream = context.getContentResolver().openInputStream(pathUri);

                Stream2File(inputStream, savePath);

                return savePath;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return path;
        }
    }

    /**
     * 获取缓存目录, 该目录的文件可以被用户清除
     * 读写该目录的文件不需要权限
     * @return 返回外部Cache目录, 或者内部的Cache目录
     */
    public File getCacheDirectory(Context context) {
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) appCacheDir = context.getExternalCacheDir();
        if (appCacheDir == null) appCacheDir = context.getCacheDir();
        return appCacheDir;
    }

    /**
     * 获取File目录, 该目录的文件不可被用户清除
     * 读写该目录的文件不需要权限
     * @return 返回外部File目录, 或者内部的File目录
     */
    public File getFileDirectory(Context context) {
        File appFileDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) appFileDir = context.getExternalFilesDir("exFiles");
        if (appFileDir == null) appFileDir = context.getFilesDir();
        return appFileDir;
    }

    /**
     * 将 Uri 转成 Bitmap
     * @param uri content://com.android.contacts/display_photo/1
     * @return Bitmap / null
     */
    public Bitmap uri2Bitmap(Context context, String uri) {
        if (context == null || TextUtils.isEmpty(uri)) return null;

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(Uri.parse(uri));
            return BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
