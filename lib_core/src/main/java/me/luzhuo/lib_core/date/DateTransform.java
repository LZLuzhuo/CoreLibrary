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

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.luzhuo.lib_core.date.enums.FormatRule;

/**
 * Description: Date and time process.
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/3/30 16:56
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class DateTransform {
    private final Map<FormatRule, SimpleDateFormat> formatRules = new HashMap<>();

    private SimpleDateFormat getFormat(FormatRule rule){
        SimpleDateFormat simpleDateFormat = formatRules.get(rule);
        if(simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat(rule.rule);
            formatRules.put(rule, simpleDateFormat);
        }
        return simpleDateFormat;
    }

    /**
     * 将指定 String类型的日期 格式化成 时间戳
     * Convert date format to timestamp
     *
     * code:
     * long timestamp = transform.date2Timestamp(FormatRule.DateAndTime, "2020-02-24 11:41:56");
     *
     * @param rule {@link FormatRule}
     * @param date exmaple: 2020-02-24 11:41:56
     * @return exmaple: 1582515716000 / -1
     */
    public long date2Timestamp(@NonNull FormatRule rule, @Nullable String date) throws ParseException {
        if (TextUtils.isEmpty(date)) return -1;

        return getFormat(rule).parse(date).getTime();
    }

    /**
     * 将 时间戳 格式化成 String类型的日期
     * Convert timestamp format to date
     *
     * code:
     * String date = transform.timestamp2Date(FormatRule.DateAndTime, timestamp);
     *
     * @param rule {@link FormatRule}
     * @param timestamp exmaple: 1582515716000
     * @return exmaple: 2020-02-24 11:41:56
     */
    @NonNull
    public String timestamp2Date(@NonNull FormatRule rule, long timestamp) {
        if (timestamp == -1) return "";

        return getFormat(rule).format(timestamp);
    }
}
