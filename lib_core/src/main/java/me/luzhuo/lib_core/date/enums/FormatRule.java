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
package me.luzhuo.lib_core.date.enums;

/**
 * Description:
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/3/30 18:22
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public enum FormatRule{
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    DateAndTime("yyyy-MM-dd HH:mm:ss"),
    /**
     * yyyy-MM-dd
     */
    Date("yyyy-MM-dd"),
    /**
     * MM-dd
     */
    MonthAndDay("MM-dd"),
    /**
     * HH:mm:ss
     */
    Time("HH:mm:ss"),
    /**
     * HH:mm
     */
    HourAndMinute("HH:mm");

    public String rule;
    private FormatRule(String rule){
        this.rule = rule;
    }
}
