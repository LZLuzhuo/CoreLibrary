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
package me.luzhuo.lib_core.app.cantacts.bean;

/**
 * Description:
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/10/4 19:19
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class ContactsBean {
    /**
     * content://com.android.contacts/display_photo/1
     */
    public String photo;
    public String name;
    /**
     * luzhuo : 155 7797 7847
     * 华为客服 : 4008308300
     */
    public String phone;

    public ContactsBean(String photo, String name, String phone) {
        this.photo = photo;
        this.name = name;
        this.phone = phone;
    }
}
