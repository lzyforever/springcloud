package com.jack.encrypt.util;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密工具
 */
public class AesEncryptUtils {
    private static final String KEY = "881234jack1988sc";
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 进行base64加密
     */
    public static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 进行base64解密
     */
    public static byte[] base64Decode(String base64Code) {
        return Base64.decodeBase64(base64Code);
    }

    /**
     * AES加密
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        return cipher.doFinal(content.getBytes("UTF-8"));
    }

    /**
     * AES加密
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    /**
     * AES解密
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception{
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }


    public static void main(String[] args) throws Exception{
        String content = "你好";
        System.out.println("加密前：" + content);

        String encrypt = aesEncrypt(content, KEY);
        System.out.println("加密后：" + encrypt);

        String decrypt = aesDecrypt(encrypt, KEY);
        System.out.println("解密后：" + decrypt);
    }


}
