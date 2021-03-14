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

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Description: 用户信息管理
 * @Author: Luzhuo
 * @Creation Date: 2021/3/14 15:05
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class BaseUserInfo {
    protected SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(CoreBaseApplication.context);

    protected String token;

    public void put(String name, String value) {
        preferences.edit().putString(name, value).apply();
    }

    public String get(String name) {
        return preferences.getString(name, "");
    }

    public void put_bool(String name, boolean value) {
        preferences.edit().putBoolean(name, value).apply();
    }

    public boolean get_bool(String name) {
        return preferences.getBoolean(name, false);
    }

    public void put_int(String name, int value) {
        preferences.edit().putInt(name, value).apply();
    }

    public int get_int(String name) {
        return preferences.getInt(name, -1);
    }

    public void put_long(String name, long value) {
        preferences.edit().putLong(name, value).apply();
    }

    public long get_long(String name) {
        return preferences.getLong(name, -1);
    }

    public void put_float(String name, float value) {
        preferences.edit().putFloat(name, value).apply();
    }

    public float get_float(String name) {
        return preferences.getFloat(name, -1F);
    }

    public void clear() {
        this.token = null;
        preferences.edit().clear().apply();
    }

    // =====================================================

    public void setToken(String token){
        if(TextUtils.isEmpty(token)) return;

        this.token = token;
        put("token", token);
    }

    public String getToken(){
        if(!TextUtils.isEmpty(token)) return this.token;

        this.token = get("token");
        return this.token;
    }
}
