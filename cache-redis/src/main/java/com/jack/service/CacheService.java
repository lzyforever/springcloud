package com.jack.service;

import java.util.concurrent.TimeUnit;

/**
 * 缓存操作接口，提供获取缓存，删除缓存操作
 */
public interface CacheService {
    /**
     * 设置缓存
     * @param key 缓存Key
     * @param value 缓存值
     * @param timeout 缓存过期时间
     * @param timeUnit 缓存过期时间
     */
    void setCache(String key, String value, long timeout, TimeUnit timeUnit);

    /**
     * 获取缓存
     * @param key 缓存Key
     * @return
     */
    String getCache(String key);

    /**
     * 获取缓存
     * @param key 缓存Key
     * @param closure 回调方法
     * @param <V>
     * @param <K>
     * @return
     */
    <V, K> String getCache(K key, Closure<V, K> closure);

    /**
     * 获取缓存
     * @param key 缓存Key
     * @param closure 回调方法
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     * @param <V>
     * @param <K>
     * @return
     */
    <V, K> String getCache(K key, Closure<V, K> closure, long timeout, TimeUnit timeUnit);

    /**
     * 删除缓存
     * @param key 缓存Key
     */
    void deleteCache(String key);
}
