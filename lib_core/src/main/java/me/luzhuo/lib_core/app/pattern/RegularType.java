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

/**
 * Description: 匹配规则
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 15:19
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public enum RegularType {

    /**
     * 我国使用的号码为11位，其中各段有不同的编码方向：前3位—网络识别号；第4-7位—地区编码；第8-11位—用户号码。
     * 号码也就是所谓的MDN号码，即本网移动用户作被叫时，主叫用户所需拨的号码，它采取E.164编码方式；存储在HLR和VLR中，在MAP接口上传送。
     *
     * MDN号码的结构如下：CC + MAC + H0 H1 H2 H3 + ABCD其中：
     * 【CC】：国家码，中国使用86。
     * 【MAC】：移动接入码，本网采用网号方案，为133。
     * 【H0H1H2H3】：HLR识别码，由运营商统一分配。
     * 【ABCD】：移动用户号，由各HLR自行分配。
     *
     * 手机号码段 (更新于2020.9.10):
     *  中国电信号段:
     *      133、153、173、177、180、181、189、190、191、193、199
     *  中国联通号段:
     *      130、131、132、145、155、156、166、167、171、175、176、185、186、196
     *  中国移动号段:
     *      134(0-8)、135、136、137、138、139、1440、147、148、150、151、152、157、158、159、172、178、182、183、184、187、188、195、197、198
     *  中国广电号段:
     *      192
     *  其他号段
     *      14号段部分为上网卡专属号段：中国联通145，中国移动147，中国电信149.
     *  虚拟运营商：
     *      电信：1700、1701、1702、162
     *      移动：1703、1705、1706、165
     *      联通：1704、1707、1708、1709、171、167
     *      卫星通信：1349、174
     *      物联网：140、141、144、146、148
     */
    MobilePhone("^(13[0-9]|14[5-9]|15[0-3,5-9]|16[2,5,6,7]|17[0-8]|18[0-9]|19[0-3,5-9])\\d{8}$"),

    /**
     * 支持:
     * LZ.Luzhuo@gmail.com
     * Luzhuo@vip.qq.com
     * LZ_Luzhuo@qq.com
     * luzhuo@126.com
     * luzhuo@vip.126.com
     *
     * 不支持
     * LZ.Luzhuo@gmail
     */
    Email("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"),

    /**
     * 身份证号码:
     * 1 1 0 1 0 2 -|- Y Y Y Y M M D D -|- 8 8 8 -|- X
     *    地址码   -|-    出生日期码    -|- 顺序及性别码 -|- 校验和
     *
     * 地址码:
     * 北京市11 天津市12 河北省13
     * 山西省14 内蒙古自治区15
     * 辽宁省21 吉林省22 黑龙江省23
     * 上海市31 江苏省32 浙江省33
     * 安徽省34 福建省35 江西省36
     * 山东省37 河南省41 湖北省42
     * 湖南省43 广东省44
     * 广西壮族自治区45 海南省46
     * 重庆市50 四川省51 贵州省52
     * 云南省53 西藏自治区54 陕西省61
     * 甘肃省62 青海省63 宁夏回族自治区64
     * 新疆维吾尔自治区65
     *
     * 位数:
     * 第一位身份证号: 15位
     * 第二代身份证号: 18位
     */
    IDCard("\\d{17}[\\d|x|X]|\\d{15}"),

    /**
     * 银行卡号: 一般是13位或者19位
     * 前六位: 发行者标识代码 Issuer Identification Number (IIN)
     * 中间位数: 个人账号标识（从卡号第七位开始）
     *      中间位数由发卡行自定义，一般由6－12位数字组成。最多可以使用12位数字。
     * 最后一位: 校验位
     */
    BankCard("[1-9]\\d{12,18}"),

    /**
     * 全部字符都为中文字符
     * 如果中间掺杂着非中文字符, 则匹配失败
     */
    Chinese("[\\u4e00-\\u9fa5]+");

    public String value () {
        return this.regular;
    }

    private String regular;
    private RegularType(String regular) {
        this.regular = regular;
    }
}
