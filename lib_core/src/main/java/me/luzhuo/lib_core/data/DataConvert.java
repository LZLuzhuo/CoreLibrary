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
package me.luzhuo.lib_core.data;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description: 数据结构转换
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/12/24 16:16
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class DataConvert {

    /**
     * 将 Cursor 转换成 HashMap 集合,
     * @param cursor 任意Cursor
     * @param closeCursor 是否关闭游标集,默认不关闭
     * @return HashMap<String, String>
     */
    @Nullable
    public ArrayList<HashMap<String, String>> cursor2HashMap(@Nullable Cursor cursor, boolean closeCursor){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        if (cursor == null || cursor.getCount() <= 0) return arrayList;

        while (cursor.moveToNext()) {
            HashMap<String, String> hashmap = new HashMap<>();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String columnName = cursor.getColumnName(i);
                String columnValue = cursor.getString(i);
                hashmap.put(columnName, columnValue);
            }
            arrayList.add(hashmap);
        }
        if (closeCursor) cursor.close();
        return arrayList;
    }

    /**
     * 将 List<String> 转成 String[]
     */
    @NonNull
    public String[] list2Array(@Nullable List<String> stringList) {
        if (stringList == null) return new String[0];

        final String[] stringArray = new String[stringList.size()];
        for (int i = 0; i < stringList.size(); i++) {
            stringArray[i] = stringList.get(i);
        }
        return stringArray;
    }

    /**
     * 将 String[] 转成 List<String>
     */
    @NonNull
    public List<String> array2List(@Nullable String[] strs) {
        final List<String> stringList = new ArrayList();
        if (strs == null) return stringList;

        for (String str : strs) {
            stringList.add(str);
        }
        return stringList;
    }

    /**
     * 将 String 转成 String[]
     */
    @NonNull
    public String[] string2Array(@Nullable String... str) {
        return str;
    }

    /**
     * 将 String 转成 List<String>
     */
    @NonNull
    public List<String> string2List(@Nullable String... str) {
        return array2List(str);
    }
}
