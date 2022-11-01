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
package me.luzhuo.lib_core.data.hashcode;

import android.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description: Base64管理
 * @Author: Luzhuo
 * @Creation Date: 2022/11/1 13:40
 * @Copyright: Copyright 2022 Luzhuo. All rights reserved.
 **/
public class Base64Manager {
    private Base64Manager(){}
    private static class Instance {
        private static final Base64Manager INSTANCE = new Base64Manager();
    }
    public static Base64Manager getInstance() {
        return Instance.INSTANCE;
    }

    @NonNull
    public String Byte2Base64(@NonNull byte[] bytes) {
        byte[] encode = Base64.encode(bytes, 0, bytes.length, Base64.DEFAULT);
        return new String(encode);
    }

    @NonNull
    public byte[] Base642Byte(@NonNull String base64) {
        byte[] decode = Base64.decode(base64, Base64.DEFAULT);
        return decode;
    }

    @NonNull
    public String String2Base64(@NonNull String content) {
        byte[] encode = Base64.encode(content.getBytes(Charset.defaultCharset()), Base64.DEFAULT);
        return new String(encode);
    }

    @NonNull
    public String Base642String(@NonNull String content) {
        byte[] decode = Base64.decode(content, Base64.DEFAULT);
        return new String(decode, Charset.defaultCharset());
    }

    @Nullable
    public String File2Base64(@NonNull InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            int length = inputStream.read(bytes);

            byte[] encode = Base64.encode(bytes, 0, length, Base64.DEFAULT);
            return new String(encode);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] Base642File(@NonNull String base64Content) {
        byte[] decode = Base64.decode(base64Content, Base64.DEFAULT);
        return decode;
    }
}
