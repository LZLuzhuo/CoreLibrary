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

import org.json.JSONArray
import org.json.JSONObject

fun JSONObject.getIntValue(name: String): Int? = if (this.has(name)) this.getInt(name) else null
fun JSONObject.getLongValue(name: String): Long? = if (this.has(name)) this.getLong(name) else null
fun JSONObject.getBooleanValue(name: String): Boolean? = if (this.has(name)) this.getBoolean(name) else null
fun JSONObject.getDoubleValue(name: String): Double? = if (this.has(name)) this.getDouble(name) else null
fun JSONObject.getStringValue(name: String): String? = if (this.has(name)) this.getString(name) else null
fun JSONArray.isEmpty(): Boolean = this.length() <= 0
fun JSONArray.isNotEmpty(): Boolean = this.length() > 0
fun JSONArray.size(): Int = this.length()
