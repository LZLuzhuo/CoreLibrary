/* Copyright 2021 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_core.date.callback;

/**
 * Description: 计时回调
 * @Author: Luzhuo
 * @Creation Date: 2021/3/14 15:45
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class ITimerCallback {
    /**
     * 计时期间回调
     * @param excuteTime 秒
     */
    public void onTask(long excuteTime) {}

    /**
     * 计时期间回调
     * @param excuteTime 00:01 秒
     */
    public void onTask(String excuteTime) {}
}
