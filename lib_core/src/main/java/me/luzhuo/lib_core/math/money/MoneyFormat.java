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
package me.luzhuo.lib_core.math.money;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;

import java.text.NumberFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description: 金钱格式化
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/6/16 21:27
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class MoneyFormat {
    private NumberChineseFormatter chineseFormatter = new NumberChineseFormatter();

    /**
     * 将String类型的数组 (如: 123.456), 格式化成小数点之后的数字都比小数点前面的数字要小一些
     * 对被格式化的内容无要求, 且从第一个小数点开始处理, 后续的小数点视为普通内容
     * get camel-case naming from money.
     * Because only the string after the decimal point are scaled by 70%, all strings with decimal points are supported.
     *
     * @param money Support formats: xxx0.00 , ￥0.00 ， 0.00
     * @return
     */
    @NonNull
    public SpannableString getCamelCase(@NonNull String money) {
        SpannableString spannableString = new SpannableString(money);
        if (money.contains(".")) {
            spannableString.setSpan(new RelativeSizeSpan(0.7f), money.indexOf("."), money.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    /**
     * 将数字格式化成(中国)货币单位
     * 123456 -> ￥123,456.00
     *
     * @param num 数字
     * @return 格式化后的数字
     */
    @NonNull
    public String formatMoney(@NonNull Object num) {
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.CHINA);
        return currency.format(num);
    }

    /**
     * 将数字格式化成指定的货币单位
     *
     * @param locale 本地化, 中国货币单位 Locale.CHINA
     * @param num    数字
     * @return 格式化后的数字
     */
    @NonNull
    public String formatMoney(@NonNull Locale locale, @NonNull Object num) {
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        return currency.format(num);
    }

    /**
     * 格式化为大写金额
     *
     * 1. 大写金额到元或角为止的, 元之后写整, 角之后可以不写, 分之后不写
     * 2. 中文大写金额数字前应标明"人民币"字样, 大写金额紧挨着"人民币"字样填写, 不得留有空白
     * 3. 阿拉伯金额数字中间有连续几个"0"时, 汉字大写金额可以只写一个"零"字
     *
     * 示范:
     * 6500元 -> 人民币陆仟伍佰元整
     * 3150.50 -> 人民币叁仟壹佰伍拾元零伍角整 / 人民币叁仟壹佰伍拾元伍角整
     * 105000.00 元 -> 人民币壹拾万伍仟元整 / 人民币壹拾万零伍仟元整
     * 60036000.00 元 -> 人民币陆仟零叁万陆仟元整
     * 35000.96 -> 人民币叁万伍仟元零玖角陆分
     * 150001.00 元 -> 人民币壹拾伍万零壹元整
     *
     * @return 壹拾伍万零壹元整 / 不支持的格式
     */
    @Nullable
    public String formatCapital(@Nullable String money) {
        if (TextUtils.isEmpty(money)) return null;

        try {
            return chineseFormatter.format(Double.parseDouble(money), true, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
