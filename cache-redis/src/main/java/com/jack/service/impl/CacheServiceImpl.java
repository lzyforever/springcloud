package com.jack.service.impl;

import com.jack.service.CacheService;
import com.jack.service.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 缓存操作实现类
 */
@Service
public class CacheServiceImpl implements CacheService {

    private Logger logger = LoggerFactory.getLogger(CacheServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private long timeout = 1L;

    private TimeUnit timeUnit = TimeUnit.HOURS;

    @Override
    public void setCache(String key, String value, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public String getCache(String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    @Override
    public <V, K> String getCache(K key, Closure<V, K> closure) {
        return doGetCache(key, closure, this.timeout, this.timeUnit);
    }

    @Override
    public <V, K> String getCache(K key, Closure<V, K> closure, long timeout, TimeUnit timeUnit) {
        return doGetCache(key, closure, timeout, timeUnit);
    }

    private <V, K> String doGetCache(K key, Closure<V, K> closure, long timeout, TimeUnit timeUnit) {
        String result = getCache(key.toString());
        if (result == null) {
            Object obj = closure.execute(key);
            setCache(key.toString(), obj.toString(), timeout, timeUnit);
            return obj.toString();
        }
        return result;
    }

    @Override
    public void deleteCache(String key) {
        stringRedisTemplate.delete(key);
    }
}
