package com.jack.encrypt.algorithm;

import com.jack.encrypt.util.AesEncryptUtils;

/**
 * AES加解密算法实现
 */
public class AesEncryptAglorithm implements EncryptAlgorithm {
    @Override
    public String encrypt(String content, String encryptKey) throws Exception {
        return AesEncryptUtils.aesEncrypt(content, encryptKey);
    }

    @Override
    public String decrypt(String content, String decryptKey) throws Exception {
        return AesEncryptUtils.aesDecrypt(content, decryptKey);
    }
}
