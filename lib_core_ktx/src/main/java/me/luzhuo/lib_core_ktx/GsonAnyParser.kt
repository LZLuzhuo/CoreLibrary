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

import com.google.gson.internal.LinkedTreeMap

/**
 * 主要用于对 Gson 返回的 Any?对象 进行解析
 *
 * 后台返回的数据结构不规范, 可使用Any?去接收
 * 然后使用该类进行解析
 */
private val Any?.isArray: Boolean get() = this is ArrayList<*>
private val Any?.isObject: Boolean get() = this is LinkedTreeMap<*, *>

data class GsonParseObject(val map: LinkedTreeMap<*, *>)
data class GsonParseArray(val array: ArrayList<*>)

val Any?.jsonArray: GsonParseArray? get() = if (this?.isArray == true) GsonParseArray(this as ArrayList<*>) else null
val Any?.jsonObj: GsonParseObject? get() = if (this?.isObject == true) GsonParseObject(this as LinkedTreeMap<*, *>) else null

fun GsonParseArray?.jsonArray(index: Int): GsonParseArray? {
    val tempArray = this?.array?.getOrNull(index)
    return if (tempArray?.isArray == true) GsonParseArray(tempArray as ArrayList<*>) else null
}
fun GsonParseArray?.jsonObj(index: Int): GsonParseObject? {
    val tempObj = this?.array?.getOrNull(index)
    return if (tempObj?.isObject == true) GsonParseObject(tempObj as LinkedTreeMap<*, *>) else null
}
fun GsonParseObject?.jsonArray(key: String): GsonParseArray? {
    val tempArray = this?.map?.get(key)
    return if (tempArray?.isArray == true) GsonParseArray(tempArray as ArrayList<*>) else null
}
fun GsonParseObject?.jsonObj(key: String): GsonParseObject? {
    val tempObj = this?.map?.get(key)
    return if (tempObj?.isObject == true) GsonParseObject(tempObj as LinkedTreeMap<*, *>) else null
}

val GsonParseArray?.size: Int get() = this?.array?.size ?: 0
val GsonParseArray?.isEmpty: Boolean get() = if (this == null) true else this.array.size <= 0
val GsonParseArray?.isNotEmpty: Boolean get() = if (this == null) false else this.array.size > 0

fun GsonParseObject?.intOrNull(key: String): Int? = this?.map?.get(key)?.int
fun GsonParseObject?.longOrNull(key: String): Long? = this?.map?.get(key)?.long
fun GsonParseObject?.doubleOrNull(key: String): Double? = this?.map?.get(key)?.double
fun GsonParseObject?.floatOrNull(key: String): Float? = this?.map?.get(key)?.float
fun GsonParseObject?.stringOrNull(key: String): String? = this?.map?.get(key)?.string
fun GsonParseObject?.boolOrNull(key: String): Boolean? = this?.map?.get(key)?.bool

fun GsonParseObject?.int(key: String): Int = this?.intOrNull(key) ?: 0
fun GsonParseObject?.long(key: String): Long = this?.longOrNull(key) ?: 0
fun GsonParseObject?.double(key: String): Double = this?.doubleOrNull(key) ?: 0.0
fun GsonParseObject?.float(key: String): Float = this?.floatOrNull(key) ?: 0.0f
fun GsonParseObject?.string(key: String): String = this?.stringOrNull(key) ?: ""
fun GsonParseObject?.bool(key: String): Boolean = this?.boolOrNull(key) ?: false