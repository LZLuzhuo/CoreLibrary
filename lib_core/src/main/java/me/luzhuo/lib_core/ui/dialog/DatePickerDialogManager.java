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
package me.luzhuo.lib_core.ui.dialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.luzhuo.lib_core.app.appinfo.AppManager;
import me.luzhuo.lib_core.ui.dialog.enums.DatePickerType;

/**
 * Description: 日期弹窗
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/10/16 21:01
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class DatePickerDialogManager {
    private final Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private static Handler mainThread = new Handler(Looper.getMainLooper());

    /**
     * 显示日历
     * @param context Context
     * @param type DatePickerType
     * @param year 年
     * @param month 月
     * @param day 日
     * @param callback OnDatePickerCallback
     */
    @MainThread
    public void show(@NonNull final Context context, @NonNull final DatePickerType type, final int year, final int month, final int day, @Nullable final OnDatePickerCallback callback){
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    show(context, type, year, month, day, callback);
                }
            });
            return;
        }

        new DatePickerDialog(context, type.themeResId(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (callback != null) callback.onDatePicker(year, monthOfYear + 1, dayOfMonth);
            }
        }, year, month - 1, day).show();
    }

    @MainThread
    public void show(@NonNull Context context, @NonNull DatePickerType type, @Nullable final OnDatePickerCallback callback){
        show(context, type, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), callback);
    }



    /**
     * 显示时间
     * @param context Context
     * @param type DatePickerType
     * @param hour 时
     * @param minute 分
     * @param callback OnTimePickerCallback
     */
    @MainThread
    public void show(@NonNull final Context context, @NonNull final DatePickerType type, final int hour, final int minute, @Nullable final OnTimePickerCallback callback) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    show(context, type, hour, minute, callback);
                }
            });
            return;
        }

        new TimePickerDialog(context, type.themeResId(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (callback != null) callback.onTimePicker(hourOfDay, minute);
            }
        }, hour, minute, true).show();
    }

    @MainThread
    public void show(@NonNull Context context, @NonNull DatePickerType type, @Nullable final OnTimePickerCallback callback) {
        show(context, type, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), callback);
    }



    public static interface OnDatePickerCallback {
        public void onDatePicker(int year, int monthOfYear, int dayOfMonth);
    }
    public static interface OnTimePickerCallback {
        public void onTimePicker(int hourOfDay, int minute);
    }
}
