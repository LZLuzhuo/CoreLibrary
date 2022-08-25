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
package me.luzhuo.lib_core.app.base;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description: 用户信息管理
 * @Author: Luzhuo
 * @Creation Date: 2021/3/14 15:05
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
@SuppressLint("ApplySharedPref")
public class BaseUserInfo {
    protected SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CoreBaseApplication.appContext);

    protected String token;

    public void put(@NonNull String name, @Nullable String value) {
        preferences.edit().putString(name, value).commit();
    }

    @NonNull
    public String get(@NonNull String name) {
        try {
            return preferences.getString(name, "");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void put_bool(@NonNull String name, boolean value) {
        preferences.edit().putBoolean(name, value).commit();
    }

    public boolean get_bool(@NonNull String name) {
        try {
            return preferences.getBoolean(name, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void put_int(@NonNull String name, int value) {
        preferences.edit().putInt(name, value).commit();
    }

    public int get_int(@NonNull String name) {
        try {
            return preferences.getInt(name, -1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void put_long(@NonNull String name, long value) {
        preferences.edit().putLong(name, value).commit();
    }

    public long get_long(@NonNull String name) {
        try {
            return preferences.getLong(name, -1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void put_float(@NonNull String name, float value) {
        preferences.edit().putFloat(name, value).commit();
    }

    public float get_float(@NonNull String name) {
        try {
            return preferences.getFloat(name, -1F);
        } catch (Exception e) {
            e.printStackTrace();
            return -1f;
        }
    }

    public void put_obj(@NonNull String name, @Nullable Serializable value) {
        if (value == null) {
            put(name, null);
            return;
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            // 将对象转为Base64
            String base64 = Byte2Base64(baos.toByteArray());
            put(name, base64);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public Object get_obj(@NonNull String name) {
        try {
            String base64 = get(name);
            if (TextUtils.isEmpty(base64)) return null;
            // 将base64转为对象
            byte[] bytes = Base642Byte(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object obj = ois.readObject();
            ois.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String Byte2Base64(@NonNull byte[] bytes) {
        byte[] encode = Base64.encode(bytes, 0, bytes.length, 0);
        return new String(encode);
    }

    private byte[] Base642Byte(@NonNull String base64) {
        byte[] decode = Base64.decode(base64, 0);
        return decode;
    }

    public void clear() {
        this.token = null;
        preferences.edit().clear().commit();
    }

    // =====================================================

    public void setToken(@Nullable String token){
        if(TextUtils.isEmpty(token)) return;

        this.token = token;
        put("token", token);
    }

    @Nullable
    public String getToken(){
        if(!TextUtils.isEmpty(token)) return this.token;

        this.token = get("token");
        return this.token;
    }
}
