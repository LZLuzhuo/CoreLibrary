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
package me.luzhuo.lib_core.app.appinfo.callback;

/**
 * Description: App前后台切换的回调
 * @Author: Luzhuo
 * @Creation Date: 2021/12/25 13:48
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public interface AppForegroundCallback {
    /**
     * App切换至前台进程
     * 1. 从桌面回到应用时, 回调
     * 2. 锁屏状态解锁时, 回调
     * 3. 从历史任务列表中进入, 回调
     */
    public void onForegroundCallback();

    /**
     * App切换至后台进程
     * 1. 从应用回到桌面时, 回调
     * 2. 进入锁屏状态时, 回调
     * 3. 查看历史人物列表, 回调
     */
    public void onBackgroundCallback();
}