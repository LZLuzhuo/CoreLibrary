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
package me.luzhuo.lib_core_ktx

import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONObject

/**
 * 主要用于对 String类型 的 json文本 进行解析
 */
data class JSONParseObject(val jsonObject: JSONObject)
data class JSONParseArray(val jsonArray: JSONArray)

val String?.jsonArray: JSONParseArray? get() = if (TextUtils.isEmpty(this)) null else try {
    JSONParseArray(JSONArray(this))
} catch (e: Exception) {
    null
}
val String?.jsonObj: JSONParseObject? get() = if (TextUtils.isEmpty(this)) null else try {
    JSONParseObject(JSONObject(this!!))
} catch (e: Exception) {
    null
}

fun JSONParseArray?.jsonArray(index: Int): JSONParseArray? {
    try {
        val tempArray = this?.jsonArray?.getJSONArray(index) ?: return null
        return JSONParseArray(tempArray)
    } catch (e: Exception) {
        return null
    }
}
fun JSONParseArray?.jsonObj(index: Int): JSONParseObject? {
    try {
        val tempObj = this?.jsonArray?.getJSONObject(index) ?: return null
        return JSONParseObject(tempObj)
    } catch (e: Exception) {
        return null
    }
}
fun JSONParseObject?.jsonArray(key: String): JSONParseArray? {
    try {
        val tempArray = this?.jsonObject?.getJSONArray(key) ?: return null
        return JSONParseArray(tempArray)
    } catch (e: Exception) {
        return null
    }
}
fun JSONParseObject?.jsonObj(key: String): JSONParseObject? {
    try {
        val tempObj = this?.jsonObject?.getJSONObject(key) ?: return null
        return JSONParseObject(tempObj)
    } catch (e: Exception) {
        return null
    }
}

fun JSONParseArray?.isEmpty(): Boolean = if (this == null) true else this.jsonArray.length() <= 0
fun JSONParseArray?.isNotEmpty(): Boolean = if (this == null) false else this.jsonArray.length() > 0
fun JSONParseArray?.size(): Int = this?.jsonArray?.length() ?: 0

fun JSONParseObject?.intOrNull(name: String): Int? = try {
    this?.jsonObject?.getInt(name)
} catch (e: Exception) {
    null
}
fun JSONParseObject?.longOrNull(name: String): Long? = try {
    this?.jsonObject?.getLong(name)
} catch (e: Exception) {
    null
}
fun JSONParseObject?.boolOrNull(name: String): Boolean? = try {
    this?.jsonObject?.getBoolean(name)
} catch (e: Exception) {
    null
}
fun JSONParseObject?.doubleOrNull(name: String): Double? = try {
    this?.jsonObject?.getDouble(name)
} catch (e: Exception) {
    null
}
fun JSONParseObject?.stringOrNull(name: String): String? = try {
    val tempStr = this?.jsonObject?.getString(name)
    if (tempStr == "null") null else tempStr
} catch (e: Exception) {
    null
}

fun JSONParseObject?.int(name: String): Int = intOrNull(name) ?: 0
fun JSONParseObject?.long(name: String): Long = longOrNull(name) ?: 0
fun JSONParseObject?.bool(name: String): Boolean = boolOrNull(name) ?: false
fun JSONParseObject?.double(name: String): Double = doubleOrNull(name) ?: 0.0
fun JSONParseObject?.string(name: String): String = stringOrNull(name) ?: ""