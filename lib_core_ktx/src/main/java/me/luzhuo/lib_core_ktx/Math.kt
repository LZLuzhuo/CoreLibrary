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