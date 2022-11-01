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

import android.text.TextUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.luzhuo.lib_core.data.SharedPreferencesManager;

/**
 * Description: 用户信息管理
 * @Author: Luzhuo
 * @Creation Date: 2021/3/14 15:05
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class BaseUserInfo {
    private final SharedPreferencesManager sharedPreferences = new SharedPreferencesManager();
    private final Cache cache = new Cache();

    protected String token;

    public void put(@NonNull String name, @Nullable String value) {
        sharedPreferences.put_string(name, value);
    }

    @NonNull
    public String get(@NonNull String name) {
        return sharedPreferences.get_string(name);
    }

    public void put_bool(@NonNull String name, boolean value) {
        sharedPreferences.put_bool(name, value);
    }

    public boolean get_bool(@NonNull String name) {
        return sharedPreferences.get_bool(name);
    }

    public void put_int(@NonNull String name, int value) {
        sharedPreferences.put_int(name, value);
    }

    public int get_int(@NonNull String name) {
        return sharedPreferences.get_int(name);
    }

    public void put_long(@NonNull String name, long value) {
        sharedPreferences.put_long(name, value);
    }

    public long get_long(@NonNull String name) {
        return sharedPreferences.get_long(name);
    }

    public void put_float(@NonNull String name, float value) {
        sharedPreferences.put_float(name, value);
    }

    public float get_float(@NonNull String name) {
        return sharedPreferences.get_float(name);
    }

    public void put_obj(@NonNull String name, @Nullable Serializable value) {
        sharedPreferences.put_obj(name, value);
    }

    @Nullable
    public Object get_obj(@NonNull String name) {
        return sharedPreferences.get_obj(name);
    }

    public void clear() {
        this.token = null;
        cache.save(this);
        sharedPreferences.clear();
        cache.restore(this);
    }

    /**
     * 重写该钩子, 实现部分缓存不清空
     */
    public void cacheSave(@NonNull Cache cache) { }

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

    public static class Cache {
        public final Map<String, String> cache_string = new HashMap<>();
        public final Map<String, Boolean> cache_bool = new HashMap<>();
        public final Map<String, Integer> cache_int = new HashMap<>();
        public final Map<String, Long> cache_long = new HashMap<>();
        public final Map<String, Float> cache_float = new HashMap<>();

        public void save(@NonNull BaseUserInfo userInfo) {
            userInfo.cacheSave(this);
        }

        public void restore(BaseUserInfo userInfo) {
            for (String value : cache_string.keySet()) { userInfo.put(value, cache_string.get(value)); }
            for (String value : cache_bool.keySet()) { userInfo.put_bool(value, cache_bool.get(value)); }
            for (String value : cache_int.keySet()) { userInfo.put_int(value, cache_int.get(value)); }
            for (String value : cache_long.keySet()) { userInfo.put_long(value, cache_long.get(value)); }
            for (String value : cache_float.keySet()) { userInfo.put_float(value, cache_float.get(value)); }
        }
    }
}