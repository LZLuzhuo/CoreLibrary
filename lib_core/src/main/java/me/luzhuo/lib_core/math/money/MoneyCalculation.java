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

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Description: 金钱计算
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/14 22:22
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class MoneyCalculation {
    private BigDecimal defaultValue;

    public MoneyCalculation(String money){
        if (TextUtils.isEmpty(money)) money = "0";
        defaultValue = new BigDecimal(money);
    }

    public MoneyCalculation(float money){
        this(String.valueOf(money));
    }

    public MoneyCalculation(double money){
        this(String.valueOf(money));
    }

    public MoneyCalculation(long money){
        this(String.valueOf(money));
    }

    public MoneyCalculation(int money){
        this(String.valueOf(money));
    }

    /**
     * 加法运算
     * 参与计算的数据类型必须转为String类型, 才能参与计算
     * Data of type float will be converted to type String, and then calculated
     */
    public MoneyCalculation add(String money) {
        if (TextUtils.isEmpty(money)) money = "0";
        defaultValue = defaultValue.add(new BigDecimal(money));
        return this;
    }

    public MoneyCalculation add(float money) {
        add(String.valueOf(money));
        return this;
    }

    public MoneyCalculation add(double money) {
        add(String.valueOf(money));
        return this;
    }

    public MoneyCalculation add(int money) {
        add(String.valueOf(money));
        return this;
    }

    public MoneyCalculation add(long money) {
        add(String.valueOf(money));
        return this;
    }

    /**
     * 减法运算
     */
    public MoneyCalculation subtract(String money) {
        if (TextUtils.isEmpty(money)) money = "0";
        defaultValue = defaultValue.subtract(new BigDecimal(money));
        return this;
    }

    public MoneyCalculation subtract(float money) {
        subtract(String.valueOf(money));
        return this;
    }

    public MoneyCalculation subtract(double money) {
        subtract(String.valueOf(money));
        return this;
    }

    public MoneyCalculation subtract(int money) {
        subtract(String.valueOf(money));
        return this;
    }

    public MoneyCalculation subtract(long money) {
        subtract(String.valueOf(money));
        return this;
    }

    /**
     * 乘法运算
     */
    public MoneyCalculation multiply(String money) {
        if (TextUtils.isEmpty(money)) money = "0";
        defaultValue = defaultValue.multiply(new BigDecimal(money));
        return this;
    }

    public MoneyCalculation multiply(float money) {
        multiply(String.valueOf(money));
        return this;
    }

    public MoneyCalculation multiply(double money) {
        multiply(String.valueOf(money));
        return this;
    }

    public MoneyCalculation multiply(int money) {
        multiply(String.valueOf(money));
        return this;
    }

    public MoneyCalculation multiply(long money) {
        multiply(String.valueOf(money));
        return this;
    }

    /**
     * 除法运算
     */
    public MoneyCalculation divide(String money) {
        if (TextUtils.isEmpty(money)) money = "1";
        defaultValue = defaultValue.divide(new BigDecimal(money), 2, RoundingMode.HALF_UP);
        return this;
    }

    public MoneyCalculation divide(float money) {
        divide(String.valueOf(money));
        return this;
    }

    public MoneyCalculation divide(double money) {
        divide(String.valueOf(money));
        return this;
    }

    public MoneyCalculation divide(int money) {
        divide(String.valueOf(money));
        return this;
    }

    public MoneyCalculation divide(long money) {
        divide(String.valueOf(money));
        return this;
    }

    /**
     * 返回保留两位小数的String类型数字
     * return calculation result with two decimal places
     */
    public String toString() {
        return defaultValue.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }
}
