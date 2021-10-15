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
package me.luzhuo.lib_core.app.email.bean;

import java.util.ArrayList;

/**
 * Description: Email的用户信息
 * @Author: Luzhuo
 * @Creation Date: 2021/10/15 23:36
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class EmailUser {
    /**
     * 收件者
     */
    private ArrayList<String> ShouJianZhe = new ArrayList<>();

    /**
     * 抄送者
     */
    private ArrayList<String> ChaoSongZhe = new ArrayList<>();

    /**
     * 密送者
     */
    private ArrayList<String> MiSongZhe = new ArrayList<>();

    /**
     * 添加收件人
     */
    public EmailUser addShouJainRen(String email) {
        this.ShouJianZhe.add(email);
        return this;
    }

    /**
     * 添加抄送人
     * 收件人 可以看到 抄送者
     */
    public EmailUser addChaoSongZhe(String email) {
        this.ChaoSongZhe.add(email);
        return this;
    }

    /**
     * 添加密送者
     * 收件人 看不到 密送者
     */
    public EmailUser addMiSongZhe(String email) {
        this.MiSongZhe.add(email);
        return this;
    }

    /**
     * 获取收件人
     */
    public String[] getShouJianRen() {
        return this.ShouJianZhe.toArray(new String[this.ShouJianZhe.size()]);
    }

    /**
     * 获取抄送者
     */
    public String[] getChaoSongZhe() {
        return this.ChaoSongZhe.toArray(new String[this.ChaoSongZhe.size()]);
    }

    /**
     * 获取密送者
     */
    public String[] getMiSongZhe() {
        return this.MiSongZhe.toArray(new String[this.MiSongZhe.size()]);
    }
}
