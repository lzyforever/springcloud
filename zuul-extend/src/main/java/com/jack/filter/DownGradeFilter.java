package com.jack.filter;

import com.jack.config.BasicConfig;
import com.jack.jwt.common.ResponseCode;
import com.jack.jwt.common.ResponseData;
import com.jack.jwt.utils.JsonUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * 服务降级过滤器
 */
public class DownGradeFilter extends ZuulFilter {

    @Autowired
    private BasicConfig basicConfig;

    public DownGradeFilter() {
        super();
    }

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 4;
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
        // 获取即将路由的服务ID
        Object serviceId = ctx.get("serviceId");
        if (serviceId != null && basicConfig != null) {
            // 获取当前Apollo配置中心中配置的需要降级的服务列表
            List<String> serviceIds = Arrays.asList(basicConfig.getDownGradeServiceStr().split(","));
            // 当需要降级时，直接在Apollo配置中心中进行配置，立马生效，当然也可以做成自动的
            // 比如监控某些指标，流量，负载等，当达到某些指标后就自动触发降级
            if (serviceIds.contains(serviceId.toString())) {
                ctx.setSendZuulResponse(false);
                ctx.set("isSuccess", false);
                ResponseData data = ResponseData.fail("服务降级中", ResponseCode.DOWNGRADE.getCode());
                ctx.setResponseBody(JsonUtils.toJson(data));
                ctx.getResponse().setContentType("application/json; charset=utf-8");
                return null;
            }
        }
        return null;
    }
}
