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
package me.luzhuo.lib_core.date;

import java.text.ParseException;

import me.luzhuo.lib_core.date.enums.CalendarRule;
import me.luzhuo.lib_core.date.enums.FormatRule;

/**
 * Description: 日期管理类
 * @Author: Luzhuo
 * @Creation Date: 2021/3/17 15:54
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class DateManager {
    private DateCalculate dateCalculate;
    private DateCalendar dateCalenda;
    private DateTransform dateTransform;

    private DateManager(){
        dateCalculate = new DateCalculate();
        dateCalenda = new DateCalendar();
        dateTransform = new DateTransform();
    }

    public static DateManager getInstance(){
        return Instance.instance;
    }
    private static class Instance{
        private static final DateManager instance = new DateManager();
    }

    public boolean isToday(long previousTimesamp){
        return dateCalculate.isToday(previousTimesamp);
    }

    public long dayDuration(long currentTimestamp, long previousTimesamp) {
        return dateCalculate.dayDuration(currentTimestamp, previousTimesamp);
    }

    public String timeDuration(long currentTimestamp, long previousTimesamp){
        return dateCalculate.timeDuration(currentTimestamp, previousTimesamp);
    }

    public String postFormat(long previousTimesamp){
        return dateCalculate.postFormat(previousTimesamp);
    }

    public String conversationFormat(long previousTimesamp){
        return dateCalculate.conversationFormat(previousTimesamp);
    }

    public long getTime(){
        return dateCalenda.getTime();
    }

    public long getTime(int year, int month, int day, int hour, int minute, int second) {
        return dateCalenda.getTime(year, month, day, hour, minute, second);
    }

    public int get(CalendarRule calendarRule){
        return dateCalenda.get(calendarRule);
    }

    public int get(CalendarRule calendarRule, long timestamp){
        return dateCalenda.get(calendarRule, timestamp);
    }

    public long addAmount(long timestamp, CalendarRule calendarRule, int amount){
        return dateCalenda.addAmount(timestamp, calendarRule, amount);
    }

    public long date2Timestamp(FormatRule rule, String date) throws ParseException {
        return dateTransform.date2Timestamp(rule, date);
    }

    public String timestamp2Date(FormatRule rule, long timestamp) {
        return dateTransform.timestamp2Date(rule, timestamp);
    }
}
