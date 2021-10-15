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

import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * 保留最多两位小数, 并舍弃末尾0
 * 12.13124123 -> 12.13
 * 12.10 -> 12.1
 * 12.00 -> 12
 */
fun Double.scale2(): String {
    return DecimalFormat("0.##").apply {
        roundingMode = RoundingMode.FLOOR
    }.format(this)
}

/**
 * 加法
 */
fun Double.add(num: Double): Double = this + num

/**
 * 减法
 */
fun Double.subtract(num: Double): Double = this - num

/**
 * 乘法
 */
fun Double.multiply(num: Double): Double = this * num

/**
 * 除法
 */
fun Double.divide(num: Double): Double = this / num

// ===

fun Float.scale2(): String {
    return DecimalFormat("0.##").apply {
        roundingMode = RoundingMode.FLOOR
    }.format(this)
}
fun Float.add(num: Float): Float = this + num
fun Float.subtract(num: Float): Float = this - num
fun Float.multiply(num: Float): Float = this * num
fun Float.divide(num: Float): Float = this / num
fun Int.add(num: Int): Int = this + num
fun Int.subtract(num: Int): Int = this - num
fun Int.multiply(num: Int): Int = this * num
fun Int.divide(num: Int): Double = this / num.toDouble()
fun Long.add(num: Int): Long = this + num
fun Long.subtract(num: Int): Long = this - num
fun Long.multiply(num: Int): Long = this * num
fun Long.divide(num: Int): Double = this / num.toDouble()