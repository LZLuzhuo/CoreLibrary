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
package me.luzhuo.lib_core.app.appinfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.security.MessageDigest;
import java.util.Locale;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description: 设备唯一ID获取工具
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/11/2 11:34
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
class DeviceIdUtils {
    @Nullable
    public String getDeviceId(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        String androidId = getAndroidId(context);
        String serial = getSerial();
        String id = getDeviceUUID();

        if (!TextUtils.isEmpty(androidId)) {
            stringBuilder.append(androidId);
            stringBuilder.append("|");
        }
        if (!TextUtils.isEmpty(serial)) {
            stringBuilder.append(serial);
            stringBuilder.append("|");
        }
        if (!TextUtils.isEmpty(id)) {
            stringBuilder.append(id);
        }

        if (stringBuilder.length() > 0) {
            try {
                byte[] hash = getHashByString(stringBuilder.toString());
                String sha1 = bytesToHex(hash);
                if (!TextUtils.isEmpty(sha1)) return sha1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getDeviceUUID() {
        String dev = "10086" + Build.BOARD +
                Build.BRAND +
                Build.DEVICE +
                Build.HARDWARE +
                Build.ID +
                Build.MODEL +
                Build.PRODUCT +
                getSerial();
        return new UUID(dev.hashCode(), getSerial().hashCode()).toString().replace("-", "");
    }

    @SuppressLint("HardwareIds")
    private String getSerial() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Build.getSerial();
            } else {
                return Build.SERIAL;
            }
        } catch (Exception e) {
            return "123321";
        }
    }

    @SuppressLint("HardwareIds")
    private String getAndroidId(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 转16进制字符串
     *
     * @param data 数据
     * @return 16进制字符串
     */
    @NonNull
    private String bytesToHex(@NonNull byte[] data) {
        StringBuilder sb = new StringBuilder();
        String stmp;
        for (int n = 0; n < data.length; n++) {
            stmp = (Integer.toHexString(data[n] & 0xFF));
            if (stmp.length() == 1)
                sb.append("0");
            sb.append(stmp);
        }
        return sb.toString().toUpperCase(Locale.CHINA);
    }

    /**
     * 取SHA1
     *
     * @param data 数据
     * @return 对应的hash值
     */
    @NonNull
    private static byte[] getHashByString(@NonNull String data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.reset();
            messageDigest.update(data.getBytes("UTF-8"));
            return messageDigest.digest();
        } catch (Exception e) {
            return "".getBytes();
        }
    }
}
