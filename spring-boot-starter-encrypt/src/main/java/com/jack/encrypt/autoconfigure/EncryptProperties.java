package com.jack.encrypt.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 加解密属性文件配置参数
 */
@ConfigurationProperties(prefix = "spring.encrypt")
public class EncryptProperties {
    /**
     * AES加密Key
     */
    private String key;

    /**
     * 字符集编码
     */
    private String charset = "UTF-8";

    /**
     * 开启调试模式，调试模式下不进行加解密操作，用于像Swagger这种在线API测试场景
     */
    private boolean debug = false;

    /**
     * 签名过期时间（分钟）
     */
    private Long signExpireTime = 10L;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Long getSignExpireTime() {
        return signExpireTime;
    }

    public void setSignExpireTime(Long signExpireTime) {
        this.signExpireTime = signExpireTime;
    }
}
