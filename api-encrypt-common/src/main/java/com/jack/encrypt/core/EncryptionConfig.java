package com.jack.encrypt.core;

import com.jack.encrypt.springboot.init.ApiEncryptDataInit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 加解密配置类
 */
@ConfigurationProperties(prefix = "spring.encrypt")
public class EncryptionConfig {

    /**
     * AES加密KEY
     */
    private String key = "881234jack1988sc";

    /**
     * 需要对响应内容进行加密的接口URI
     * 比如：/user/list
     * 不支持@PathVariable格式的URI
     */
    private List<String> responseEncryptUriList = new ArrayList<>();

    /**
     * 需要对请求内容进行解密的接口URI
     * 比如：/user/list
     * 不支持@PathVariable格式的URI
     */
    private List<String> requestDecryptUriList = new ArrayList<>();

    /**
     * 忽略对响应内容进行加密的接口URI
     * 比如：/user/list
     * 不支持@PathVariable格式的URI
     */
    private List<String> responseEncryptUriIgnoreList = new ArrayList<>();

    /**
     * 忽略对请求内容进行解密的接口URI
     * 比如：/user/list
     * 不支持@PathVariable格式的URI
     */
    private List<String> requestDecryptUriIgnoreList = new ArrayList<>();

    /**
     * 响应数据编码
     */
    private String responseCharset = "UTF-8";

    /**
     * 开启调试模式，调试模式下不进行加解密操作，用于像Swagger这种在线API测试场景
     */
    private boolean debug = false;

    /**
     * 过滤器拦截器模式
     */
    private String[] urlPatterns = new String[]{"/*"};

    /**
     * 过滤器执行顺序
     */
    private int order = 1;

    public EncryptionConfig() {
        super();
    }

    public EncryptionConfig(String key, List<String> responseEncryptUriList, List<String> requestDecryptUriList, String responseCharset, boolean debug) {
        super();
        this.key = key;
        this.responseEncryptUriList = responseEncryptUriList;
        this.requestDecryptUriList = requestDecryptUriList;
        this.responseCharset = responseCharset;
        this.debug = debug;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getResponseEncryptUriList() {
        // 合并注解与配置文件两种方式
        return Stream.of(responseEncryptUriList, ApiEncryptDataInit.responseEncryptUriList).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void setResponseEncryptUriList(List<String> responseEncryptUriList) {
        this.responseEncryptUriList = responseEncryptUriList;
    }

    public List<String> getRequestDecryptUriList() {
        // 合并注解与配置文件两种方式
        return Stream.of(requestDecryptUriList, ApiEncryptDataInit.requestDecryptUriList).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void setRequestDecryptUriList(List<String> requestDecryptUriList) {
        this.requestDecryptUriList = requestDecryptUriList;
    }

    public List<String> getResponseEncryptUriIgnoreList() {
        // 合并注解与配置文件两种方式
        return Stream.of(responseEncryptUriIgnoreList, ApiEncryptDataInit.responseEncryptUriIgnoreList).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void setResponseEncryptUriIgnoreList(List<String> responseEncryptUriIgnoreList) {
        this.responseEncryptUriIgnoreList = responseEncryptUriIgnoreList;
    }

    public List<String> getRequestDecryptUriIgnoreList() {
        // 合并注解与配置文件两种方式
        return Stream.of(requestDecryptUriIgnoreList, ApiEncryptDataInit.requestDecryptUriIgnoreList).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void setRequestDecryptUriIgnoreList(List<String> requestDecryptUriIgnoreList) {
        this.requestDecryptUriIgnoreList = requestDecryptUriIgnoreList;
    }

    public String getResponseCharset() {
        return responseCharset;
    }

    public void setResponseCharset(String responseCharset) {
        this.responseCharset = responseCharset;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String[] getUrlPatterns() {
        return urlPatterns;
    }

    public void setUrlPatterns(String[] urlPatterns) {
        this.urlPatterns = urlPatterns;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<String> getRequestDecryptParams(String uri) {
        List<String> params = ApiEncryptDataInit.requestDecryptParamMap.get(uri);
        if (CollectionUtils.isEmpty(params)) {
            return new ArrayList<>();
        }
        return params;
    }
}
