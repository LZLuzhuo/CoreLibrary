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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: Email的内容
 * @Author: Luzhuo
 * @Creation Date: 2021/10/15 23:36
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class EmailContent {
    /**
     * 邮件的标题
     */
    private String emailTitle = "";

    /**
     * 邮件的内容
     */
    private String emailContent = "";

    /**
     * 附件
     */
    private List<File> emailAttachment = new ArrayList<>();

    /**
     * 设置邮件标题
     */
    public EmailContent setEmailTitle(String title) {
        this.emailTitle = title;
        return this;
    }

    /**
     * 设置邮件内容
     */
    public EmailContent setEmailContent(String emailContent) {
        this.emailContent = emailContent;
        return this;
    }

    /**
     * 添加邮件附件
     */
    public EmailContent addAttachment(File file) {
        if (file == null || !file.exists()) return this;
        this.emailAttachment.add(file);
        return this;
    }

    /**
     * 获取邮件标题
     */
    public String getEmailTitle() {
        return this.emailTitle;
    }

    /**
     * 获取邮件内容
     */
    public String getEmailContent() {
        return this.emailContent;
    }

    /**
     * 获取邮件附件
     * Android6.0及以下, 可以发送同一个文件
     * Android7.0及以上, 不能发送同一个文件; 如果你选择同一个文件, 对导致邮件一直处于发送中
     */
    public List<File> getEmailAttachment() {
        return this.emailAttachment;
    }
}
