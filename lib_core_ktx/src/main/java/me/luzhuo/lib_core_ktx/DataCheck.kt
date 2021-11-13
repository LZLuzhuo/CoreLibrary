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
 * 后台返回的数据结构不规范, 可使用Any?去接收
 * 使用方式:
 *
 * Object:
 * if (t?.supplier.isObject) {
 *     val obj = t?.supplier?.toObject
 *     Log.e(TAG, "" + obj?.get("name"));
 *     Log.e(TAG, "" + obj?.get("age"));
 * }
 *
 * Array:
 * val array = t?.supplier?.toArray
 * Log.e(TAG, "" + array?.getObj(1))
 */
val Any?.isArray: Boolean get() = this is ArrayList<*>
val Any?.isObject: Boolean get() = this is LinkedTreeMap<*, *>
val Any?.toArray: ArrayList<*>? get() = if (this?.isArray == true) this as? ArrayList<*> else null
val Any?.toObject: LinkedTreeMap<*, *>? get() = if (this?.isObject == true) this as? LinkedTreeMap<*, *> else null
fun ArrayList<*>?.getObj(index: Int): Any? {
    if (index < 0 || index >= this?.size ?: 0 - 1) return null
    return this?.get(index)
}
fun LinkedTreeMap<*, *>?.getObj(key: String): Any? {
    return this?.get(key)
}

val Any?.int: Int? get() = try {
    this?.toString()?.toFloat()?.toInt()
} catch (e: Exception) {
    null
}
val Any?.long: Long? get() = try {
    this?.toString()?.toDouble()?.toLong()
} catch (e: Exception) {
    null
}
val Any?.double: Double? get() = try {
    this?.toString()?.toDouble()
} catch (e: Exception) {
    null
}
val Any?.float: Float? get() = try {
    this?.toString()?.toFloat()
} catch (e: Exception) {
    null
}
val Any?.string: String? get() = this?.toString()