/* Copyright 2020 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_core.ui.dialog.enums;

import android.app.DatePickerDialog;
import android.content.Context;

import me.luzhuo.lib_core.app.appinfo.AppManager;

/**
 * Description: 日期弹窗样式
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/10/16 21:02
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public enum DatePickerType {
    DEFAULT(0, 0),
    DEVICE_DEFAULT(DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT, DatePickerDialog.THEME_DEVICE_DEFAULT_DARK),
    HOLO(DatePickerDialog.THEME_HOLO_LIGHT, DatePickerDialog.THEME_HOLO_DARK),
    TRADITIONAL(DatePickerDialog.THEME_TRADITIONAL, DatePickerDialog.THEME_TRADITIONAL);

    private int themeResId_Light, themeResId_Dark;
    private DatePickerType(int themeResId_Light, int themeResId_Dark) {
        this.themeResId_Light = themeResId_Light;
        this.themeResId_Dark = themeResId_Dark;
    }

    public int themeResId(){
        if (new AppManager().isDarkTheme()) return themeResId_Dark;
        return themeResId_Light;
    }
}
