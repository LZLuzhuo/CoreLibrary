/* Copyright 2022 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_core.data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.luzhuo.lib_core.app.base.CoreBaseApplication;
import me.luzhuo.lib_core.data.hashcode.Base64Manager;

/**
 * Description: SharedPreferences 键值对存储到本地
 * @Author: Luzhuo
 * @Creation Date: 2022/11/1 13:47
 * @Copyright: Copyright 2022 Luzhuo. All rights reserved.
 **/
public class SharedPreferencesManager {
    protected SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CoreBaseApplication.appContext);

    public void put_string(@NonNull String name, @Nullable String value) {
        preferences.edit().putString(name, value).commit();
    }

    @NonNull
    public String get_string(@NonNull String name) {
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
            return preferences.getInt(name, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void put_long(@NonNull String name, long value) {
        preferences.edit().putLong(name, value).commit();
    }

    public long get_long(@NonNull String name) {
        try {
            return preferences.getLong(name, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void put_float(@NonNull String name, float value) {
        preferences.edit().putFloat(name, value).commit();
    }

    public float get_float(@NonNull String name) {
        try {
            return preferences.getFloat(name, 0F);
        } catch (Exception e) {
            e.printStackTrace();
            return 0f;
        }
    }

    public void put_obj(@NonNull String name, @Nullable Serializable value) {
        if (value == null) {
            put_string(name, null);
            return;
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(value);
            // 将对象转为Base64
            String base64 = Base64Manager.getInstance().Byte2Base64(baos.toByteArray());
            put_string(name, base64);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public Object get_obj(@NonNull String name) {
        try {
            String base64 = get_string(name);
            if (TextUtils.isEmpty(base64)) return null;
            // 将base64转为对象
            byte[] bytes = Base64Manager.getInstance().Base642Byte(base64);
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

    public void clear() {
        preferences.edit().clear().commit();
    }
}
