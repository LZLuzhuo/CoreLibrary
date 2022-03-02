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

import androidx.annotation.Nullable;

/**
 * Description: 可空数据监测
 * 用于解决Java中基本数据类型为null时, 不会智能转换, 并且拆箱时报空指针异常
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 15:40
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class DataCheck {
	public static boolean check(@Nullable Boolean bool) {
		if (bool == null) return false;
		return bool;
	}

	public static long check(@Nullable Long lon) {
		if (lon == null) return 0;
		return lon;
	}
	
	public static int check(@Nullable Integer inte) {
		if (inte == null) return 0;
		return inte;
	}
	
	public static float check(@Nullable Float flo) {
		if (flo == null) return 0f;
		return flo;
	}
	
	public static double check(@Nullable Double doub) {
		if (doub == null) return 0;
		return doub;
	}
}
