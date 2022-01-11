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
val Any?.bool: Boolean? get() = try {
    this?.toString()?.toBoolean()
} catch (e: Exception) {
    null
}

val Int?.int: Int get() = this ?: 0
val Long?.long: Long get() = this ?: 0
val Double?.double: Double get() = this ?: 0.0
val Float?.float: Float get() = this ?: 0.0f
val String?.string: String get() = this ?: ""
val Boolean?.bool: Boolean get() = this ?: false