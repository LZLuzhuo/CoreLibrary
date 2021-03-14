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
package me.luzhuo.lib_core.ui.notification.bean;

import android.content.Intent;

import androidx.annotation.DrawableRes;

/**
 * Description: 通知类
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/9/24 0:39
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class NotificationBean {
    /**
     * 标题
     */
    public String title;
    /**
     * 内容
     */
    public String content;
    /**
     * 图标
     */
    public @DrawableRes int icon;
    /**
     * 意图
     */
    public Intent intent;

    public NotificationBean(String title, String content, @DrawableRes int icon) {
        this.title = title;
        this.content = content;
        this.icon = icon;
    }

    public NotificationBean(String title, String content, @DrawableRes int icon, Intent intent) {
        this.title = title;
        this.content = content;
        this.icon = icon;
        this.intent = intent;
    }
}
