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

import android.app.NotificationManager;

/**
 * Description: 通知渠道
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/9/24 0:21
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class ChannelBean {
    /**
     * 渠道等级
     */
    public static enum ChannelImportance {
        HIGH(NotificationManager.IMPORTANCE_HIGH), DEFAULT(NotificationManager.IMPORTANCE_DEFAULT), LOW(NotificationManager.IMPORTANCE_LOW), MIN(NotificationManager.IMPORTANCE_MIN);
        private int importance;
        private ChannelImportance(int importance) {
            this.importance = importance;
        }

        public int value() {
            return importance;
        }
    }

    /**
     * 渠道id, 随意起
     */
    public String channelID;
    /**
     * 显示给用户看的渠道名
     */
    public String chancelName;
    /**
     * 通知重要等级(高->低): NotificationManager.IMPORTANCE_HIGH, NotificationManager.IMPORTANCE_DEFAULT, NotificationManager.IMPORTANCE_LOW, NotificationManager.IMPORTANCE_MIN
     */
    public ChannelImportance importance;

    public ChannelBean(String channelID, String chancelName, ChannelImportance importance) {
        this.channelID = channelID;
        this.chancelName = chancelName;
        this.importance = importance;
    }
}

