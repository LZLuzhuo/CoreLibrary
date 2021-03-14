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
package me.luzhuo.lib_core.data;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 字符串工具
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 15:44
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class StringManager {

	/**
	 * 是否含有中文字符 (不检测标点符号)
	 * @param str 字符串
	 * @return true含有, false不含有
	 */
	public static boolean isContainChinese(String str) {
		if (TextUtils.isEmpty(str)) return false;

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) return true;
        return false;
    }
}
