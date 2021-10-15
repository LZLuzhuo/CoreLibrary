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

fun Int.dp(): Int = UICalculation(CoreBaseApplication.context).px2dp(this.toFloat())
fun Float.dp(): Int = UICalculation(CoreBaseApplication.context).px2dp(this)
fun Int.px(): Int = UICalculation(CoreBaseApplication.context).dp2px(this.toFloat())
fun Float.px(): Int = UICalculation(CoreBaseApplication.context).dp2px(this)

/**
 * 将dp转成系统需要的px
 * 注意:
 * 10.dp 此处的含义是将10dp转成系统需要的px
 * 10.px() 此处的含义是将10dp转成系统需要的px
 */
val Int.dp: Int get() = UICalculation(CoreBaseApplication.context).dp2px(this.toFloat())
val Float.dp: Int get() = UICalculation(CoreBaseApplication.context).dp2px(this)

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