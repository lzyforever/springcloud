package com.jack.encrypt.springboot.endpoint;

import com.jack.encrypt.core.EncryptionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

import java.util.HashMap;
import java.util.Map;

/**
 * 加解密配置信息端点
 */
@Endpoint(id = "encrypt-config")
public class EncryptEndpoint {
    @Autowired
    private EncryptionConfig encryptionConfig;

    @ReadOperation
    public Map<String, Object> encryptConfig() {
        Map<String, Object> data = new HashMap<>();
        data.put("responseEncryptUriList", encryptionConfig.getResponseEncryptUriList());
        data.put("responseEncryptUriIgnoreList", encryptionConfig.getResponseEncryptUriIgnoreList());
        data.put("requestDecryptUriList", encryptionConfig.getRequestDecryptUriList());
        data.put("requestDecryptUriIgnoreList", encryptionConfig.getRequestDecryptUriIgnoreList());
        return data;
    }
}
