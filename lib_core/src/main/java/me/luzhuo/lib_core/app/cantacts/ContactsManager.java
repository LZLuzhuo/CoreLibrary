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
package me.luzhuo.lib_core.app.cantacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import me.luzhuo.lib_core.app.cantacts.bean.ContactsBean;

/**
 * Description: 联系人管理类
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/10/4 19:19
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class ContactsManager {
    /**
     * 获取手机联系人信息
     * 需要权限 android.permission.READ_CONTACTS
     */
    @NonNull
    public List<ContactsBean> getContacts(@NonNull Context context) {
        List<ContactsBean> list = new ArrayList();

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String photo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

            list.add(new ContactsBean(photo, name, phone));
        }
        cursor.close();
        return list;
    }
}
