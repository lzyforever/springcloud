package com.jack.encrypt.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * AES加解密工具类
 */
public class AesEncryptUtils {

    private AesEncryptUtils() {
    }

    /**
     * 密钥算法类型
     */
    public static final String KEY_ALGORITHM = "AES";

    /**
     * 密钥的默认位长度
     */
    public static final int DEFAULT_KEY_SIZE = 128;

    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ECB_PKCS_5_PADDING = "AES/ECB/PKCS5Padding";

    public static final String ECB_NO_PADDING = "AES/ECB/NoPadding";


    /**
     * 将字节数组转成Base64编码
     *
     * @param bytes 字节数组
     * @return Base64编码后的字符串
     */
    public static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 将字符串通过Base64解码，转成字节数组
     *
     * @param base64Code Base64编码的字符串
     * @return Base64解码后的字节数组
     */
    public static byte[] base64Decode(String base64Code) {
        return Base64.decodeBase64(base64Code);
    }

    /**
     * 生成Hex格式默认长度的随机密钥
     * 字符串长度为32，解二进制后为16个字节
     *
     * @return String Hex格式的随机密钥
     */
    public static String initHexKey() {
        return Hex.encodeHexString(initKey());
    }

    /**
     * 生成默认长度的随机密钥
     *
     * @return
     */
    public static byte[] initKey() {
        return initKey(DEFAULT_KEY_SIZE);
    }

    /**
     * 生成密钥
     * 128、192、256 可选
     *
     * @param keySize 密钥长度
     * @return 二进制密钥
     */
    public static byte[] initKey(int keySize) {
        if (keySize != 128 && keySize != 192 && keySize != 256) {
            throw new RuntimeException("error keySize：" + keySize);
        }
        // 实例化KeyGenerator
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("no such algorithm exception：" + KEY_ALGORITHM, e);
        }
        keyGenerator.init(keySize);
        // 生成密钥
        SecretKey secretKey = keyGenerator.generateKey();
        // 获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key密钥
     */
    private static Key toKey(byte[] key) {
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密后的数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        return encrypt(data, key, KEY_ALGORITHM);
    }

    /**
     * 加密
     *
     * @param data      待加密数据
     * @param key       密钥
     * @param algorithm 算法/工作模式/填充模式
     * @return byte[] 加密后的数据
     */
    public static byte[] encrypt(byte[] data, byte[] key, String algorithm) {
        Key k = toKey(key);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(algorithm);
            // 初始化，设置为加密模式
            cipher.init(Cipher.ENCRYPT_MODE, k);

            // 发现使用NoPadding时，使用ZeroPadding填充
            if (ECB_NO_PADDING.equals(algorithm)) {
                return cipher.doFinal(formatWithZeroPadding(data, cipher.getBlockSize()));
            }
            // 执行操作
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("AES encrypt error", e);
        }
    }

    /**
     * 格式化填充模式为NoPadding的情况
     *
     * @param data      加密数据
     * @param blockSize 块大小
     * @return 格式化后的数据
     */
    private static byte[] formatWithZeroPadding(byte[] data, final int blockSize) {
        final int length = data.length;
        final int remainLength = length % blockSize;
        if (remainLength > 0) {
            byte[] inputData = new byte[length + blockSize - remainLength];
            System.arraycopy(data, 0, inputData, 0, length);
            return inputData;
        }
        return data;
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密的数据
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        return decrypt(data, key, ECB_PKCS_5_PADDING);
    }

    public static byte[] decrypt(byte[] data, byte[] key, String algorithm) {
        Key k = toKey(key);
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            // 初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, k);

            // 发现使用NoPadding时，使用ZeroPadding填充
            if (ECB_PKCS_5_PADDING.equals(algorithm)) {
                return removeZeroPadding(cipher.doFinal(data), cipher.getBlockSize());
            }
            // 执行操作
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("AES decrypt error", e);
        }
    }

    /**
     * 去掉ZeroPadding的填充
     *
     * @param data      解密数据
     * @param blockSize 块大小
     * @return 去除填充后的数据
     */
    private static byte[] removeZeroPadding(byte[] data, final int blockSize) {
        final int length = data.length;
        final int remainLength = length % blockSize;

        if (remainLength == 0) {
            int i = length - 1;
            while (i >= 0 && 0 == data[i]) {
                i--;
            }
            byte[] outputData = new byte[i + 1];
            System.arraycopy(data, 0, outputData, 0, outputData.length);
            return outputData;
        }
        return data;
    }

    public static byte[] aesEncryptToBytes(String content, String hexAesKey) throws Exception {
        return encrypt(content.getBytes(StandardCharsets.UTF_8), Hex.decodeHex(hexAesKey.toCharArray()));
    }

    public static String aesEncrypt(String content, String hexAesKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, hexAesKey));
    }

    public static String aesDecryptByBytes(byte[] encryptBytes, String hexAesKey) throws Exception {
        byte[] decrypt = decrypt(encryptBytes, Hex.decodeHex(hexAesKey.toCharArray()));
        return new String(decrypt, StandardCharsets.UTF_8);
    }

    public static String aesDecrypt(String encryptStr, String hexAesKey) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptStr), hexAesKey);
    }

}
