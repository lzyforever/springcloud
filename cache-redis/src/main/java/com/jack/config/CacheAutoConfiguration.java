package com.jack.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存自动配置类
 * 主要是配置当redis挂掉后，程序连接不上时也不会影响业务功能，而是会继续执行查询数据库的操作
 */
@Configuration
public class CacheAutoConfiguration extends CachingConfigurerSupport {

    private Logger logger = LoggerFactory.getLogger(CacheAutoConfiguration.class);

    /**
     * redis数据操作异常处理
     * 这里的处理方式是：在日志中打印出错误信息，但是放行，保证redis服务器出现连接
     * 等问题的时候不影响程序的正常运行，使得能够出问题时不用缓存，继续执行业务逻辑去查询数据库
     */
    @Bean
    public CacheErrorHandler errorHandler() {
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                logger.error("redis 异常: key={}", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                logger.error("redis 异常: key={}", key, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                logger.error("redis 异常: key={}", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                logger.error("redis 异常: ", e);
            }
        };
        return cacheErrorHandler;
    }
}
