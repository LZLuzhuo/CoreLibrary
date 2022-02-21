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

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import me.luzhuo.lib_core.date.enums.CalendarRule;
import me.luzhuo.lib_core.date.enums.TimeRule;

/**
 * Description: 日期时间计算
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/3/30 17:12
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class DateCalculate {
    private static final String TAG = DateCalculate.class.getSimpleName();
    private DateCalendar calendar = new DateCalendar();

    /**
     * 是否是今天
     * long timeEquation = (currentTimestamp - previousTimesamp) / (3600 * 24 * 1000)
     * if time[0,1] is today.
     *
     * return true:
     * current: 2020-03-30 12:12:12 - previous: 2020-03-30 12:12:12
     * current: 2020-03-30 12:12:12 - previous: 2020-03-30 12:12:11
     *
     * return false:
     * current: 2020-03-30 12:12:12 - previous: 2020-03-29 12:12:11
     * current: 2020-03-30 12:12:12 - previous: 2020-03-29 12:12:12
     *
     * @param previousTimesamp previous time, it < currentTimestamp. example: 1582515716000
     * @return if timeEquation in time[0,1] return true.
     */
    public boolean isToday(long previousTimesamp){
        long currentTimestamp = System.currentTimeMillis();
        double timeEquation = (Math.abs(currentTimestamp - previousTimesamp)) / (double) TimeRule.Day.timeLength;

        if (calendar.get(CalendarRule.Day, currentTimestamp) == calendar.get(CalendarRule.Day, previousTimesamp) && timeEquation <= 1) return true;
        return false;
    }

    /**
     * 日级别的时间间距
     * long dayDuration = currentDay - previousDay.
     * Excluding today.
     *
     * 2020-03-30 - 2020-03-29 = 1 day
     * 2020-03-29 - 2020-03-30 = -1 day
     * 2020-03-30 - 2020-03-30 = 0 day
     * 2020-03-29 12:12:12 - 2020-03-29 12:12:11 = 0 day
     * 2020-03-29 12:12:11 - 2020-03-29 12:12:12 = 0 day
     *
     * @param currentTimestamp it > previousTimesamp. example: 1582515716000
     * @param previousTimesamp example: 1582515716000
     * @return Excluding today
     */
    public long dayDuration(long currentTimestamp, long previousTimesamp) {
        return (currentTimestamp - previousTimesamp) / TimeRule.Day.timeLength;
    }

    /**
     * 格式化时间间距, 一般用于IM通讯的时间
     * Format time duration
     * xxx天x小时x分
     *
     * Example:
     * <pre>
     * calculate.timeDuration(
     * calendar.getTime(2022, 12, 30, 12, 12, 11),
     * calendar.getTime(2019, 12, 30, 12, 12, 11)));
     * </pre>
     *
     * @param currentTimestamp it > previousTimesamp. example: 1582515716000
     * @param previousTimesamp example: 1582515716000
     * @return xxx天x小时x分, return 0分 if currentTimestamp < previousTimesamp
     */
    public String timeDuration(long currentTimestamp, long previousTimesamp){
        long timeDiffer = Math.abs(currentTimestamp - previousTimesamp);
        if(timeDiffer / TimeRule.Minute.timeLength <= 0) return "0分";

        String timeFormat = "";
        long dayDiffer = timeDiffer / TimeRule.Day.timeLength;
        if(dayDiffer > 0) timeFormat += (dayDiffer + "天");
        long hourDiffer = (timeDiffer % TimeRule.Day.timeLength) / TimeRule.Hour.timeLength;
        if(hourDiffer > 0) timeFormat += (hourDiffer + "小时");
        long minuteDiffer = (timeDiffer % TimeRule.Hour.timeLength) / TimeRule.Minute.timeLength;
        if(minuteDiffer > 0) timeFormat += (minuteDiffer + "分");
        return timeFormat;
    }

    /**
     * 格式化时间与当前时间的间距, 一般用于发帖的时间
     * Formatting post-specific time format
     * >48h yyyy年MM月dd日(日期)
     * 24<x<48h 昨天
     * 1<x<24h x小时前
     * 1<x<60m x分钟前
     * 1<x<60s 刚刚
     * @return
     */
    public String postFormat(long previousTimesamp){
        long currentTimestamp = System.currentTimeMillis();
        if(currentTimestamp / TimeRule.Minute.timeLength < previousTimesamp / TimeRule.Minute.timeLength) throw new IllegalArgumentException("must be previousTimesamp: " + previousTimesamp + " < " + " currentTimestamp: " + currentTimestamp);

        long timesamp = currentTimestamp - previousTimesamp;
        if (timesamp <= TimeRule.Minute.timeLength)
            return "刚刚";
        else if (timesamp > TimeRule.Minute.timeLength && timesamp <= TimeRule.Hour.timeLength)
            return timesamp / TimeRule.Minute.timeLength + "分钟前";
        else if (timesamp > TimeRule.Hour.timeLength && timesamp <= TimeRule.Day.timeLength)
            return timesamp / TimeRule.Hour.timeLength + "小时前";
        else if (timesamp > TimeRule.Day.timeLength && timesamp <= TimeRule.Day2.timeLength)
            return "昨天";
        else
            return new SimpleDateFormat("yyyy年MM月dd日").format(previousTimesamp);
    }

    /**
     * 格式化时间与当前时间的间距, 一般用于IM通讯的时间
     * Formatting im conversation time format
     * Today 19:09
     * Yesterday 昨天
     * Before yesterday 前天
     * Inside week 周一
     * Inside year 7月10日
     * Outside year 2019年12月24日
     * @return
     */
    public String conversationFormat(long previousTimesamp){
        long currentTimestamp = System.currentTimeMillis();
        if(currentTimestamp / TimeRule.Minute.timeLength < previousTimesamp / TimeRule.Minute.timeLength) {
            Log.w(TAG, "must be previousTimesamp: " + previousTimesamp + " < " + " currentTimestamp: " + currentTimestamp);
            return new SimpleDateFormat("HH:mm").format(previousTimesamp);
        }

        Calendar calendar_old = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"), Locale.CHINA);
        calendar_old.setTimeInMillis(previousTimesamp);

        Calendar calendar_now = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"), Locale.CHINA);
        calendar_now.setTimeInMillis(currentTimestamp);

        String fomatString = null;
        if (calendar_now.get(Calendar.YEAR) > calendar_old.get(Calendar.YEAR)) {
            fomatString = new SimpleDateFormat("yyyy年MM月dd日").format(previousTimesamp);
        } else {
            if (getWeekOfYear(calendar_now) > getWeekOfYear(calendar_old)){
                // In China, Sunday is counted as last week, and the week starts counting from Monday.
                fomatString =  new SimpleDateFormat("MM月dd日").format(previousTimesamp);
            } else {
                switch(calendar_old.get(Calendar.DAY_OF_WEEK)){
                    case Calendar.SUNDAY:
                        fomatString = "周日";
                        break;
                    case Calendar.MONDAY:
                        fomatString = "周一";
                        break;
                    case Calendar.TUESDAY:
                        fomatString = "周二";
                        break;
                    case Calendar.WEDNESDAY:
                        fomatString = "周三";
                        break;
                    case Calendar.THURSDAY:
                        fomatString = "周四";
                        break;
                    case Calendar.FRIDAY:
                        fomatString = "周五";
                        break;
                    case Calendar.SATURDAY:
                        fomatString = "周六";
                        break;
                }
            }
        }

        // Date correction
        int dayDuration = calendar_now.get(Calendar.DAY_OF_YEAR) - calendar_old.get(Calendar.DAY_OF_YEAR);
        switch(dayDuration){
            case 0:
                fomatString =  new SimpleDateFormat("HH:mm").format(previousTimesamp);
                break;
            case 1:
                fomatString = "昨天";
                break;
            case 2:
                fomatString = "前天";
                break;
        }

        return fomatString;
    }

    /**
     * 获取一年中的第几周
     */
    private int getWeekOfYear(Calendar calendar) {
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) week -= 1;
        return week;
    }
}
