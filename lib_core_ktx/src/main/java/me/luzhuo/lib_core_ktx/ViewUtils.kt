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

import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.constraintlayout.widget.Group
import me.luzhuo.lib_core.app.base.CoreBaseApplication
import me.luzhuo.lib_core.ui.calculation.UICalculation

/**
 * Group 的群点击事件
 */
fun Group.setOnClickListeners(listener: (View) -> Unit) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}

// 单击时间间隔, 1秒 (单位纳秒)
private const val singleLaunchInterval = 1 * 1000 * 1000 * 1000
// 上次有效的运行时间
private var lastLaunchTime: Long = 0
// 上次有效的运行时间
private var lastClickTime: Long = 0

/**
 * 防止重复运行
 */
fun launch2(function: () -> Unit) {
    if (System.nanoTime() - lastLaunchTime >= singleLaunchInterval) {
        function.invoke()
        lastLaunchTime = System.nanoTime()
    }
}

/**
 * 防止重复点击的点击事件
 */
fun View.setOnClickListener2(listener: (View) -> Unit) {
    this.setOnClickListener {
        if (System.nanoTime() - lastClickTime >= singleLaunchInterval) {
            listener.invoke(it)
            lastClickTime = System.nanoTime()
        }
    }
}

fun Int.px2dp(): Int = UICalculation(CoreBaseApplication.appContext).px2dp(this.toFloat())
fun Float.px2dp(): Int = UICalculation(CoreBaseApplication.appContext).px2dp(this)
fun Int.dp2px(): Int = UICalculation(CoreBaseApplication.appContext).dp2px(this.toFloat())
fun Float.dp2px(): Int = UICalculation(CoreBaseApplication.appContext).dp2px(this)

/**
 * 将dp转成系统需要的px
 * 注意:
 * 10.dp 此处的含义是将10dp转成系统需要的px
 * 10.dp2px() 此处的含义是将10dp转成系统需要的px
 */
val Int.dp: Int get() = UICalculation(CoreBaseApplication.appContext).dp2px(this.toFloat())
val Float.dp: Int get() = UICalculation(CoreBaseApplication.appContext).dp2px(this)

fun visible(vararg views: Any?) { views.forEach { (it as? View)?.visibility = View.VISIBLE } }
fun invisible(vararg views: Any?) { views.forEach { (it as? View)?.visibility = View.INVISIBLE } }
fun gone(vararg views: Any?) { views.forEach { (it as? View)?.visibility = View.GONE } }

/**
 * 隐藏密码
 */
fun EditText?.passwordHide() {
    this?.inputType = InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD
}
fun EditText?.passwordShow() {
    this?.inputType = InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
}
fun EditText?.passwordHideNumber() {
    this?.inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_VARIATION_PASSWORD
}
fun EditText?.passwordShowNumber() {
    this?.inputType = InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL
}

/**
 * View 的 margin 值
 */
var View.marginTop: Int
    get() = (this.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin ?: 0
    set(value) { (this.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin = value }

var View.marginBottom: Int
    get() = (this.layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: 0
    set(value) { (this.layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin = value }

var View.marginLeft: Int
    get() = (this.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin ?: 0
    set(value) { (this.layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin = value }

var View.marginRight: Int
    get() = (this.layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin ?: 0
    set(value) { (this.layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin = value }