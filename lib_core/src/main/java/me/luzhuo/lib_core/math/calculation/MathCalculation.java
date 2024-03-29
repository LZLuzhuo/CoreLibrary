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
package me.luzhuo.lib_core.math.calculation;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

/**
 * Description: 数学计算
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/27 23:25
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class MathCalculation {

    /**
     * 获取数组中最大的数
     * @param nums int类型数组
     * @return 最大的数
     */
    public int max(@NonNull int[] nums){
        int max_num = 0;
        for (int num : nums) {
            max_num = max_num > num ? max_num : num;
        }
        return max_num;
    }

    /**
     * 上标数字
     * @param number m^2
     */
    @NonNull
    public SpannableString getSuperCase(@NonNull String number) {
        SpannableString spannableString = new SpannableString(number);

        if (number.contains("^")) {
            int superIndex = number.indexOf("^");
            number = number.replace("^", "");

            spannableString = new SpannableString(number);
            spannableString.setSpan(new RelativeSizeSpan(0.7f), superIndex, number.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new SuperscriptSpan(), superIndex, number.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }
        return spannableString;
    }

    /**
     * 完成的百分比
     * <pre>
     *     任务1完成比   | 任务2完成比    | 完成百分比(计算结果)
     *     0            |   0           | 0
     *     0            |   0.5         | 0.25
     *     0.5          |   0           | 0.25
     *     0.5          |   0.5         | 0.5
     *     1            |   0.5         | 0.75
     *     0.5          |   1           | 0.75
     *     1            |   1           | 1
     * </p>
     * 任务完成比 = min((实际完成量 / 应完成量), 1f)
     * @param values Pair(实际完成量, 应完成量)
     * @return
     */
    public float completePercent(Pair<Integer, Integer>... values) {
        float totalCompletePercent = 0f;
        for (int i = 0; i < values.length; i++) {
            totalCompletePercent += Math.min(values[i].first.floatValue() / values[i].second.floatValue(), 1f);
        }
        return totalCompletePercent / values.length;
    }
}
