package com.jack.filter;

import com.jack.jdbc.utils.IpUtils;
import com.jack.jdbc.utils.JsonUtils;
import com.jack.vo.ResponseCode;
import com.jack.vo.ResponseData;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义一个pre过滤器
 * 处理IP黑名单
 *
 * 过滤器自定义完成之后，需要配置过滤器才能生效。
 * 
 */
public class IpFilter extends ZuulFilter {

    /** IP黑名单列表 */
    private List<String> blackIpList = Arrays.asList("127.0.0.1");

    public IpFilter() {
        super();
    }

    /**
     * 过滤器类型
     * 类型有：pre、route、post、error
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序，数值越小，优先级越高
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     *  是否执行该过滤器，true为执行，false为不执行
     *  这个也可以利用配置中心来实现，达到动态的开启和关闭过滤器
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Object success = ctx.get("isSuccess");
        return success == null ? true : Boolean.parseBoolean(success.toString());
        //return true;
    }

    /**
     *  执行自已的业务逻辑，本段代码中是通过判断请求的IP是否在黑名单中，决定是否进行拦截
     *  blackIpList字段是IP的黑名单，判断条件成立之后，通过设置ctx.setSendZuulResponse(false)
     *  告诉Zuul不需要将当前请求转发到后端的服务了。通过setResponseBody返回数据给客户端
     *
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("进入了自定义过滤器...");
        RequestContext ctx = RequestContext.getCurrentContext();
        String ip = IpUtils.getIpAddress(ctx.getRequest());
        //try {
            // 如果在这里发生了异常，进行了try catch处理，不抛出异常，则不会进行到ErrorFilter当中
            // 如果再throw出去，就会进入到ErrorFilter过滤器中进行捕获
        //    System.out.println(2/0);
        //} catch (Exception e) {
        //    System.out.println("发生异常了。。。");
        //    throw new RuntimeException();
        //}
        // 在黑名单中禁用
        if (StringUtils.isNotBlank(ip) && blackIpList.contains(ip)) {
            // 拦截请求并返回客户端信息
            // ctx.setSendZuulResponse(false)将告诉Zuul不需要将当前请求转发到后端的服务。
            // 原理体现在shouldFilter()方法上，源码在org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter的shouldFilter()方法里。
            ctx.setSendZuulResponse(false);
            // ctx.set("sendForwardFilter.ran", true)是用来拦截本地转发请求的，当
            // 配置了forward:/local的路由，ctx.setSendZuulResponse(false)对forward是不起作用的，需要
            // 设置ctx.set(”sendForwardFilter.ran“, true)才行，对应的实现源码
            // 在org.springframework.cloud.netflix.zuul.filters.route.SendForwardFilter的shouldFilter()方法中
            ctx.set("sendForwardFilter.ran", true);
            // 到这一步之后，当前的过滤器确实将请求进行了拦截了，并且可以给客户端返回信息。但是当
            // 项目中有多个过滤器的时候，假如需要过滤的那个过滤器是第一个执行的，发现非法请求，然后进
            // 行了拦截，以javax.servlet.Filter的经验，进行拦截后，在chain.doFilter之前进行返回就可以
            // 让过滤器不往下执行了。但是zuul中的过滤器不一样，即使刚刚通过ctx.setSendZuulResponse(false)设置
            // 了不路由到服务，并且返回null，那只是当前的过滤器执行完了，后面还有很多过滤器在等着执行。逻辑
            // 在源码FiilterProcessor.runFilters()方法里面，获取了所有过滤器，并循环执行。
            // 通过分析就知道为什么所有的过滤器都要执行了，解决这个问题的办法就是通过shouldFilter来处理，即
            // 在拦截之后通过数据传递的方式告诉下一个过滤器是否要执行。
            // 在拦截中添加以下的数据传递代码，这里示例里面只有一个拦截器，所以可以不用添加
            // ctx.set("isSuccess", false);
            ResponseData data = ResponseData.fail("非法请求", ResponseCode.NO_AUTH_CODE.getCode());
            ctx.setResponseBody(JsonUtils.toJson(data));
            ctx.getResponse().setContentType("application/json; charset=utf-8");
            return null;
        }
        return null;
    }
}
