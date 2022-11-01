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

import android.text.TextUtils;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description: 哈希序列管理
 * 以支持的加解密算法:
 * 1. 对称加密:
 * 2. 非对称加密: RSA
 * 3. 签名算法: MD5, SHA, UUID
 * 4. 编码算法: Base64
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
    private final Base64Manager base64 = Base64Manager.getInstance();

    private HashManager() { }
    public static HashManager getInstance(){
        return Instance.instance;
    }

    private static class Instance {
        private static final HashManager instance = new HashManager();
    }

    /**
     * 获取uuid
     * UUID是世界唯一的随机码
     * @return 71771d258ac7494e8cc0f74739e44b88
     */
    @NonNull
    public String getUuid() {
        return UUID.randomUUID().toString().replace("-","");
    }

    @NonNull
    public String getUuid(@NonNull String name) {
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
    @Nullable
    public String getBase64(@NonNull HashAction action, @Nullable String content) {
        if (TextUtils.isEmpty(content)) return null;

        if (action == HashAction.Encode) {
            return base64.String2Base64(content);
        } else if (action == HashAction.Decode) {
            return base64.Base642String(content);
        }
        return null;
    }

    /**
     * 将文件转成 Base64
     * @param inputStream 文件输入流
     * @return Base64编码
     */
    @Nullable
    public String getFileToBase64(@NonNull InputStream inputStream) {
        return base64.File2Base64(inputStream);
    }

    /**
     * 将 Base64 转成 byte[] , 可以直接使用 FileOutputStream 写入到文件
     * @param base64Content Base64 文本内容
     * @return
     */
    @Nullable
    public byte[] getBase64ToFile(@Nullable String base64Content) {
        if (TextUtils.isEmpty(base64Content)) return null;
        return base64.Base642File(base64Content);
    }

    /**
     * MD5 消息摘要
     * @param content 待摘要的文本内容
     * @return MD5消息摘要
     */
    @NonNull
    public String getMD5(@Nullable String content) {
        return messageDigest(MD5, content);
    }
    @Nullable
    public String getMD5(@Nullable InputStream inputStream) {
        return messageDigest(MD5, inputStream);
    }
    @Nullable
    public String getSHA1(@Nullable String content) {
        return messageDigest(SHA1, content);
    }
    @Nullable
    public String getSHA1(@Nullable InputStream inputStream) {
        return messageDigest(SHA1, inputStream);
    }
    @Nullable
    public String getSHA224(@Nullable String content) {
        return messageDigest(SHA224, content);
    }
    @Nullable
    public String getSHA224(@Nullable InputStream inputStream) {
        return messageDigest(SHA224, inputStream);
    }
    @Nullable
    public String getSHA256(@Nullable String content) {
        return messageDigest(SHA256, content);
    }
    @Nullable
    public String getSHA256(@Nullable InputStream inputStream) {
        return messageDigest(SHA256, inputStream);
    }
    @Nullable
    public String getSHA384(@Nullable String content) {
        return messageDigest(SHA384, content);
    }
    @Nullable
    public String getSHA384(@Nullable InputStream inputStream) {
        return messageDigest(SHA384, inputStream);
    }
    @Nullable
    public String getSHA512(@Nullable String content) {
        return messageDigest(SHA512, content);
    }
    @Nullable
    public String getSHA512(@Nullable InputStream inputStream) {
        return messageDigest(SHA512, inputStream);
    }

    @Nullable
    private String messageDigest(@NonNull String algorithm, @Nullable String content) {
        if (TextUtils.isEmpty(content)) return null;

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) { e.printStackTrace(); }

        messageDigest.update(content.getBytes(Charset.defaultCharset()));
        byte[] digest = messageDigest.digest();

        return new BigInteger(1, digest).toString(16);
    }

    @Nullable
    private String messageDigest(@NonNull String algorithm, @Nullable InputStream inputStream) {
        if (inputStream == null) return null;

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

    /**
     * 使用公钥进行加密
     * @param publicKey 公钥
     * @param plainText 需要被加密的明文
     * @return 加密后的密文
     */
    @Nullable
    public String RSAEncrypt(@NonNull @RSAUtil.RAS_Transformation String transformation, @NonNull String publicKey, @NonNull String plainText) {
        return new RSAUtil(transformation).encrypt(publicKey, plainText);
    }

    /**
     * 使用私钥进行解密
     * @param privateKey 私钥
     * @param enStr 被加密的密文
     * @return 解密后的明文
     */
    @Nullable
    public String RSADecrypt(@NonNull @RSAUtil.RAS_Transformation String transformation, @NonNull String privateKey, @NonNull String enStr) {
        return new RSAUtil(transformation).decrypt(privateKey, enStr);
    }
}
