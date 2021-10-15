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
package me.luzhuo.lib_core.data.hashcode;

import android.util.Base64;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Description: 哈希序列管理
 *
 * @Author: Luzhuo
 * @Creation Date: 2020/5/15 15:09
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class HashManager {
    private static final String MD5 = "MD5";
    private static final String SHA1 = "SHA-1";
    private static final String SHA224 = "SHA-224";
    private static final String SHA256 = "SHA-256";
    private static final String SHA384 = "SHA-384";
    private static final String SHA512 = "SHA-512";

    private HashManager(){}
    public static HashManager getInstance(){
        return Instance.instance;
    }

    private static class Instance{
        private static final HashManager instance = new HashManager();
    }

    /**
     * 获取uuid
     * UUID是世界唯一的随机码
     * @return 71771d258ac7494e8cc0f74739e44b88
     */
    public String getUuid() {
        return UUID.randomUUID().toString().replace("-","");
    }

    public String getUuid(String name) {
        try {
            return UUID.nameUUIDFromBytes(name.getBytes("UTF-8")).toString().replace("-","");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Base64转换
     * @param action Hash动作 解码/编码
     * @param content 待转换的文本内容, 或者 Base64 内容
     * @return 转换后的文本内容, 或者 Base64 内容
     */
    public String getBase64(HashAction action, String content) {
        if (action == HashAction.Encode) {
            byte[] encode = Base64.encode(content.getBytes(Charset.defaultCharset()), Base64.DEFAULT);
            return new String(encode);
        } else if (action == HashAction.Decode) {
            byte[] decode = Base64.decode(content, Base64.DEFAULT);
            return new String(decode, Charset.defaultCharset());
        }
        return null;
    }

    /**
     * 将文件转成 Base64
     * @param inputStream 文件输入流
     * @return Base64编码
     */
    public String getFileToBase64(InputStream inputStream) {
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

    /**
     * 将 Base64 转成 byte[] , 可以直接使用 FileOutputStream 写入到文件
     * @param base64Content Base64 文本内容
     * @return
     */
    public byte[] getBase64ToFile(String base64Content) {
        byte[] decode = Base64.decode(base64Content, Base64.DEFAULT);
        return decode;
    }

    /**
     * MD5 消息摘要
     * @param content 待摘要的文本内容
     * @return MD5消息摘要
     */
    public String getMD5(String content) {
        return messageDigest(MD5, content);
    }
    public String getMD5(InputStream inputStream) {
        return messageDigest(MD5, inputStream);
    }
    public String getSHA1(String content) {
        return messageDigest(SHA1, content);
    }
    public String getSHA1(InputStream inputStream) {
        return messageDigest(SHA1, inputStream);
    }
    public String getSHA224(String content) {
        return messageDigest(SHA224, content);
    }
    public String getSHA224(InputStream inputStream) {
        return messageDigest(SHA224, inputStream);
    }
    public String getSHA256(String content) {
        return messageDigest(SHA256, content);
    }
    public String getSHA256(InputStream inputStream) {
        return messageDigest(SHA256, inputStream);
    }
    public String getSHA384(String content) {
        return messageDigest(SHA384, content);
    }
    public String getSHA384(InputStream inputStream) {
        return messageDigest(SHA384, inputStream);
    }
    public String getSHA512(String content) {
        return messageDigest(SHA512, content);
    }
    public String getSHA512(InputStream inputStream) {
        return messageDigest(SHA512, inputStream);
    }

    private String messageDigest(String algorithm, String content) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        messageDigest.update(content.getBytes(Charset.defaultCharset()));
        byte[] digest = messageDigest.digest();

        return new BigInteger(1, digest).toString(16);
    }

    private String messageDigest(String algorithm, InputStream inputStream) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance(algorithm);

            int len;
            byte[] buffer = new byte[2048];
            while ((len = inputStream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, len);
            }
        } catch (NoSuchAlgorithmException e) { e.printStackTrace();
        } catch (FileNotFoundException e) { e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        byte[] digest = messageDigest.digest();

        return new BigInteger(1, digest).toString(16);
    }
}
