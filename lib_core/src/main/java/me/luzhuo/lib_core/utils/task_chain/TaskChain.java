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
package me.luzhuo.lib_core.utils.task_chain;

import androidx.annotation.NonNull;

/**
 * Description: 任务链
 * 按顺序执行的任务链
 *
 * @Author: Luzhuo
 * @Creation Date: 2022/2/22 20:51
 * @Copyright: Copyright 2022 Luzhuo. All rights reserved.
 **/
public class TaskChain {
    // 开始的第一个任务
    private Task startTask = null;
    // 结束的最后一个任务
    private Task endTask = null;

    /**
     * 添加任务
     * @param task 新任务
     */
    public void addTask(@NonNull Task task) {
        if (startTask == null) startTask = task;

        if (endTask != null) endTask.next = task;
        endTask = task;
    }

    /**
     * 运行任务
     */
    public void start() {
        if (startTask == null) return;
        startTask.action();
    }
}
