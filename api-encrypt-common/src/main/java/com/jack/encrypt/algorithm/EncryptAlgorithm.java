package com.jack.encrypt.algorithm;

/**
 * 加解密算法接口
 */
public interface EncryptAlgorithm {

    /**
     * 加密
     *
     * @param content    加密字符串
     * @param encryptKey 加密Key
     * @return 加密内容
     * @throws Exception 加密异常
     */
    String encrypt(String content, String encryptKey) throws Exception;

    /**
     * 解密
     *
     * @param content    解密字符串
     * @param decryptKey 解密Key
     * @return 解密内容
     * @throws Exception 解密异常
     */
    String decrypt(String content, String decryptKey) throws Exception;
}
