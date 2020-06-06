package com.jack.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.jack.config.BasicConfig;
import com.jack.jwt.common.ResponseCode;
import com.jack.jwt.common.ResponseData;
import com.jack.jwt.utils.JsonUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 集群限流过滤器
 *
 * PS：单个的限流过滤器，就不需要使用Redis了，直接用RateLimiter就可以做到
 *
 * RateLimiter使用
 * create方法的参数是每秒种产生的令牌数量
 * acquire 方法是以阻塞的方式获取令牌
 * tryAcquire(int permits, long timeout, TimeUnit unit) 方法是来设置等待超时时间的方式获取令牌，如果超timeout为0，则代表非阻塞，获取不到立即返回。
 *
 */
public class ClusterLimitFilter extends ZuulFilter {
    private Logger log = LoggerFactory.getLogger(ClusterLimitFilter.class);

    /** 每秒种产生100个令牌，create方法的参数是每秒种产生的令牌数量 */
    public static volatile RateLimiter rateLimiter = RateLimiter.create(100.0);

    @Autowired
    @Qualifier("longRedisTemplate")
    private RedisTemplate<String, Long> redisTemplate;

    @Autowired
    private BasicConfig basicConfig;

    public ClusterLimitFilter() {
        super();
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Long currentSecond = System.currentTimeMillis() / 1000;
        // 自定义一个key
        String key = "jack-api-rate-limit-" + currentSecond;
        try {
            // 如果不存在就创建一个key
            if (!redisTemplate.hasKey(key)) {
                redisTemplate.opsForValue().set(key, 0L, 100, TimeUnit.SECONDS);
            }

            // 当集群中当前秒的并发量达到了设定的值，不进行处理，注意集群中的网关所在服务器时间必须同步
            if (redisTemplate.opsForValue().increment(key, 1) > basicConfig.getClusterLimitRate()) {
                ctx.setSendZuulResponse(false);
                ctx.set("isSuccess", false);
                ResponseData data = ResponseData.fail("当前负载太高，请稍后重试", ResponseCode.LIMIT_ERROR_CODE.getCode());
                ctx.setResponseBody(JsonUtils.toJson(data));
                ctx.getResponse().setContentType("application/json; charset=utf-8");
                return null;
            }
        } catch (Exception e) {
            log.error("集群限流异常", e);
            // Redis挂掉等异常处理，可以继续单节点限流
            // 单节点限流
            rateLimiter.acquire();
        }
        return null;
    }
}
