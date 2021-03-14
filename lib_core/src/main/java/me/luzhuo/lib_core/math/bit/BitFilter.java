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
package me.luzhuo.lib_core.math.bit;

/**
 * Description:  bit位筛选, 用于由bit位组成的筛选条件
 *
 * 		   		| 市中心 | 有VR  | 限贷	| 电梯房 | 复式 	| 需求符合度
 * 目标房源		| 0		| 1		| 0		| 1		| 0		|
 * 需求1	    | 1		| 1		| 0		| 1		| 0		| 不符合
 * 需求2	    | 0		| 0		| 0		| 1		| 0		| 符合
 * 需求3	    | 0		| 0		| 1		| 0		| 1		| 不符合
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 16:08
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class BitFilter {
	/**
	 * 判断是否符合筛选规则
	 * @param target 目标数
	 * @param accorded 被匹配的符合数 
	 */
	public boolean isAccord(int target, int accorded){
		return (target & accorded) == accorded;
	}
	
	/**
	 * 修改指定位, 对指定位取反:
	 * 	如果指定位为1, 则修改为0;
	 * 	如果指定位为0, 则修改为1
	 * @param target 目标数
	 * @param index 从右往左, 0开始
	 * @return 修改后的数
	 */
	public int update(int target, int index) {
		return target ^ (1 << index);	
	}
	
	/**
	 * 修改指定位: 
	 *  bit位true,将指定位置1;
	 *  bit为false, 将指定位置0
	 * @param target 目标数
	 * @param index 从右往左, 0开始
	 * @param bit true为1, false为0
	 * @return
	 */
	public int update(int target, int index, boolean bit) {
		if (bit) return target | (1 << index);
		else return target & ~(1 << index);
	}
	
	/**
	 * 判断指定为是否为1, 如果为1则返回true, 否则返回false
	 * @param target 目标数
	 * @param index 从右往左, 0开始
	 * @return 判断结果, 为1返回true, 为0返回false
	 */
	public boolean check(int target, int index) {
		return (target & 1 << index) == 1 << index;
	}
}
