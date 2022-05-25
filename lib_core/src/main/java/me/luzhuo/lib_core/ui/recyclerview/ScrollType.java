/* Copyright 2022 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_core.ui.recyclerview;

/**
 * Description: RecyclerView的滚动类型
 * @Author: Luzhuo
 * @Creation Date: 2022/5/24 13:44
 * @Copyright: Copyright 2022 Luzhuo. All rights reserved.
 **/
public enum ScrollType {
    /**
     * 每次都滚动到顶部
     */
    Start,
    /**
     * 每次到滚动到底部
     */
    End,
    /**
     * 每次滚动到中部
     */
    Center,
    /**
     * 在视野范围内不滚动
     * 不在视野范围内, 将其移到视野范围内, 位置可能在顶部页可能在底部, 有原来的位置在上方还是在下方决定的
     */
    Any
}