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
package me.luzhuo.lib_core.date;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import me.luzhuo.lib_core.date.enums.CalendarRule;

/**
 * Description: 日历, 以时间戳为中间媒介
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/4/24 10:50
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class DateCalendar {

    @NonNull
    private Calendar getCalendar(){
        /**
         * This Calendar must be rebuild each time it is used.
         *
         * set the time zone form the Beijing eighth district
         * set the local calendar form china calendar
         */
        Calendar rightNow = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"), Locale.CHINA);
        return rightNow;
    }

    /**
     * 获取当前时间戳
     * Get the timestamp of the right now time
     * @return 1587699543436
     */
    public long getTime(){
        return getCalendar().getTimeInMillis();
    }

    public long getTime(int year, int month, int day, int hour, int minute, int second){
        Calendar calendar = getCalendar();
        // The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0; the last depends on the number of months in a year.
        calendar.set(year, month - 1, day, hour, minute, second);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当前时间戳的年, 月, 日, 时, 分, 秒
     * Get the current year, month, day of month, hour,minute, second.
     * @param calendarRule CalendarRule
     * @return 2020
     */
    public int get(CalendarRule calendarRule){
        switch (calendarRule){
            case Year:
                return getCalendar().get(Calendar.YEAR);
            case Month:
                return getCalendar().get(Calendar.MONTH) + 1;
            case Day:
                return getCalendar().get(Calendar.DAY_OF_MONTH);
            case Hour:
                return getCalendar().get(Calendar.HOUR_OF_DAY);
            case Minute:
                return getCalendar().get(Calendar.MINUTE);
            case Second:
                return getCalendar().get(Calendar.SECOND);
            default:
                return -1;
        }
    }

    /**
     * 获取指定时间戳的年, 月, 日, 时, 分, 秒
     * Get the year, month, day of month, hour,minute, second, by timestamp
     * @param timestamp 1587699543436
     * @return 2020
     */
    public int get(CalendarRule calendarRule, long timestamp){
        Calendar calendar = getCalendar();
        calendar.setTimeInMillis(timestamp);

        switch (calendarRule){
            case Year:
                return calendar.get(Calendar.YEAR);
            case Month:
                return calendar.get(Calendar.MONTH) + 1;
            case Day:
                return calendar.get(Calendar.DAY_OF_MONTH);
            case Hour:
                return calendar.get(Calendar.HOUR_OF_DAY);
            case Minute:
                return calendar.get(Calendar.MINUTE);
            case Second:
                return calendar.get(Calendar.SECOND);
            default:
                return -1;
        }
    }

    /**
     * 在时间戳上增减指定间隙的时间
     * add or subtract date.
     *
     * example:
     * <pre>
     * long timeDuration = calendar.addAmount(calendar.getTime(2019, 12, 30, 12, 12, 11), CalendarRule.Day, 15);
     * String dateStr = transform.timestamp2Date(FormatRule.DateAndTime, timeDuration);
     * </pre>
     *
     * @param timestamp 1587699543436
     * @param calendarRule CalendarRule
     * @param amount + is add, - is subtract
     * @return timestamp 1587699543436
     */
    public long addAmount(long timestamp, CalendarRule calendarRule, int amount){
        if(amount == 0) return timestamp;

        Calendar calendar = getCalendar();
        calendar.setTimeInMillis(timestamp);

        switch (calendarRule){
            case Year:
                calendar.add(Calendar.YEAR, amount);
                break;
            case Month:
                calendar.add(Calendar.MONTH, amount);
                break;
            case Day:
                calendar.add(Calendar.DAY_OF_MONTH, amount);
                break;
            case Hour:
                calendar.add(Calendar.HOUR_OF_DAY, amount);
                break;
            case Minute:
                calendar.add(Calendar.MINUTE, amount);
                break;
            case Second:
                calendar.add(Calendar.SECOND, amount);
                break;
        }
        return calendar.getTimeInMillis();
    }
}
