package com.jack.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.jack.jwt.common.ResponseCode;
import com.jack.jwt.common.ResponseData;
import com.jack.jwt.utils.JsonUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单节点限流过滤器
 *
 * 直接用RateLimiter就可以做到
 *
 * RateLimiter使用
 * create方法的参数是每秒种产生的令牌数量
 * acquire 方法是以阻塞的方式获取令牌
 * tryAcquire(int permits, long timeout, TimeUnit unit) 方法是来设置等待超时时间的方式获取令牌，如果超timeout为0，则代表非阻塞，获取不到立即返回。
 *
 */
public class LimitFilter extends ZuulFilter {
    private Logger log = LoggerFactory.getLogger(LimitFilter.class);

    /** 每秒种产生100个令牌，create方法的参数是每秒种产生的令牌数量 */
    public static volatile RateLimiter rateLimiter = RateLimiter.create(100.0);

    public LimitFilter() {
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
        // 此方法判断是否可以从rateLimiter立即获得令牌
        // 如果获取不到令牌，进行拦截
        if(!rateLimiter.tryAcquire()){
            ctx.setSendZuulResponse(false);
            ctx.set("isSuccess", false);
            ResponseData data = ResponseData.fail("当前负载太高，请稍后重试", ResponseCode.LIMIT_ERROR_CODE.getCode());
            ctx.setResponseBody(JsonUtils.toJson(data));
            ctx.getResponse().setContentType("application/json; charset=utf-8");
        }
        return null;
    }
}
