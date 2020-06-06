package com.jack.filter;

import com.jack.config.BasicConfig;
import com.jack.support.RibbonFilterContextHolder;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 灰度发布过滤器
 * 将配置信息传递到自定义的负载均衡类中去
 */
public class GrayPushFilter extends ZuulFilter {

    @Autowired
    private BasicConfig basicConfig;

    public GrayPushFilter() {
        super();
    }

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 6;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Object success = ctx.get("isSuccess");
        return success == null || Boolean.parseBoolean(success.toString());
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        // AuthFilter验证成功之后设置的用户编号
        String loginUserId = ctx.getZuulRequestHeaders().get("uid");
        RibbonFilterContextHolder.getCurrentContext().add("userId", loginUserId);
        // 灰度发布的服务信息
        RibbonFilterContextHolder.getCurrentContext().add("servers", basicConfig.getGrayPushServers());
        // 灰度发布的用户ID信息
        RibbonFilterContextHolder.getCurrentContext().add("userIds", basicConfig.getGrayPushUsers());
        return null;
    }

}
