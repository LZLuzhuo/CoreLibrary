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
import android.view.Gravity
import android.widget.Toast
import me.luzhuo.lib_core.app.base.CoreBaseApplication
import me.luzhuo.lib_core.ui.calculation.UICalculation
import me.luzhuo.lib_core.ui.toast.ToastManager

/**
 * 吐司
 */
fun Any.toast(content: String?) {
    if(TextUtils.isEmpty(content)) return
    ToastManager.show(CoreBaseApplication.context, content)
}

private var height = 0
fun Any.toast2(content: String?) {
    if(height == 0) height = UICalculation(CoreBaseApplication.context).display[1] / 3
    Toast.makeText(CoreBaseApplication.context, content, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.TOP, 0, height)
    }.show()
}

/**
 * 断言数据不符合要求, 弹出吐司, 并永久返回false
 */
fun Any.assertNoToast(content: String?): Boolean {
    toast(content)
    return false
}

fun Any.assertNoToast2(content: String?): Boolean {
    toast2(content)
    return false
}