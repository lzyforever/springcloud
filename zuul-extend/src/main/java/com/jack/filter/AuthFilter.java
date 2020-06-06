package com.jack.filter;

import com.jack.config.BasicConfig;
import com.jack.jwt.common.ResponseCode;
import com.jack.jwt.common.ResponseData;
import com.jack.jwt.utils.JWTUtils;
import com.jack.jwt.utils.JsonUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 认证过滤器
 */
public class AuthFilter extends ZuulFilter {

    /** 注入基础配置 */
    @Autowired
    private BasicConfig basicConfig;

    public AuthFilter() {
        super();
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 编写过滤器的各种判断逻辑
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        String token = ctx.getRequest().getHeader("Authorization");
        JWTUtils jwtUtils = JWTUtils.getInstance();
        // 获取配置的白名单信息
        String apis = basicConfig.getApiWhiteStr();
        // 将配置的白名单转成List，然后判断当前请求是URI是否在白名单中
        List<String> whiteApis = Arrays.asList(apis.split(","));
        String uri = ctx.getRequest().getRequestURI();
        // 在白名单中，则放过
        if (whiteApis.contains(uri)) {
            return null;
        }

        // path 处理
        // 解决像/user/{userId}这种类型的URI，URI中有动态参数，直接像上面那样匹配是不行的，需要做了处理再判断
        for (String wapi : whiteApis) {
            if (wapi.contains("{") && wapi.contains("}")) {
                if (wapi.split("/").length == uri.split("/").length) {
                    String reg = wapi.replaceAll("\\{.*}", ".*{1,}");
                    System.err.println(reg);
                    Pattern r = Pattern.compile(reg);
                    Matcher m = r.matcher(uri);
                    if (m.find()) {
                        return null;
                    }
                }
            }
        }

        // 验证Token
        // 1、判断是携带了token
        if (!StringUtils.hasText(token)) {
            ctx.setSendZuulResponse(false);
            ctx.set("isSuccess", false);
            ResponseData data = ResponseData.fail("非法请求【缺少Authorization信息】", ResponseCode.NO_AUTH_CODE.getCode());
            ctx.setResponseBody(JsonUtils.toJson(data));
            ctx.getResponse().setContentType("application/json; charset=utf-8");
            return null;
        }

        // 2、校验token的合法性
        JWTUtils.JWTResult jwt = jwtUtils.checkToken(token);
        if (!jwt.isStatus()) {
            ctx.setSendZuulResponse(false);
            ctx.set("isSuccess", false);
            ResponseData data = ResponseData.fail(jwt.getMsg(), jwt.getCode());
            ctx.setResponseBody(JsonUtils.toJson(data));
            ctx.getResponse().setContentType("application/json; charset=utf-8");
            return null;
        }

        System.err.println("用户ID：" + jwt.getUid());
        // 向下游微服务中传递认证之后的用户信息
        ctx.addZuulRequestHeader("uid", jwt.getUid());
        return null;
    }

}
