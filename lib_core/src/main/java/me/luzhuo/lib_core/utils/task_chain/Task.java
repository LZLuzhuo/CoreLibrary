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

/**
 * Description: 任务
 * 自定义的任务需要继承该类, 并重写 action() 函数.
 *
 * @Author: Luzhuo
 * @Creation Date: 2022/2/22 20:52
 * @Copyright: Copyright 2022 Luzhuo. All rights reserved.
 **/
public abstract class Task {
    /**
     * 下一个节点
     */
    public Task next;

    /**
     * 具体要运行的任务
     */
    public abstract void action();

    /**
     * 该任务已完成
     * 该任务完成必须调用该函数, 才能继续执行下个任务
     */
    public void complete() {
        if (next == null) return;
        next.action();
    }
}
