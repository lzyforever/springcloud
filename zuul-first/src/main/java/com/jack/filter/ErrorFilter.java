package com.jack.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义异常过滤器
 * 对于异常来说，无论在哪个地方都需要处理。过滤器中的异常主要发
 * 生在run方法中，可以用try catch语句来处理。Zuul中也为我们提供了一个异
 * 常处理的过滤器，当过滤器在执行过程中发生异常，若没有被捕获到，就会进入error过滤器中。
 */
public class ErrorFilter extends ZuulFilter {

    private Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 100;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("进入了自定义异常过滤器");
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        log.error("Filter Error: {}", throwable.getCause().getMessage());
        return null;
    }
}
