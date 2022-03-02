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
package me.luzhuo.lib_core.app.pattern;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description: 常见的正则检测
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 15:19
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class PatternCheck {
    /**
     * 检查数据是否符合正则规则
     * @param type RegularType
     * @param data 被检查的数据
     * @return 符合正则返回true, 否则返回false
     */
    public boolean check(@NonNull RegularType type, @Nullable String data){
        return this.check(type.value(), data);
    }

    /**
     * 检测数据是否符合正则规则
     * @param regular 自定义的正则规则
     * @param data 被检查的数据
     * @return 符合正则返回true, 否则返回false
     */
    public boolean check(@NonNull String regular, @Nullable String data) {
        if (TextUtils.isEmpty(data)) return false;

        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }
}
