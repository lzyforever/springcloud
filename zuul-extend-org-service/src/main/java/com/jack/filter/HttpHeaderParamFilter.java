package com.jack.filter;

import com.jack.support.RibbonFilterContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 接收Zuul传递过来的用户信息的过滤器
 * 里面对Zuul传递过来的信息存放到 RibbonFilterContextHolder 里面
 * RibbonFilterContextHolder 是通过 InheritableThreadLocal 在线程之间进行数据传递的
 * 这里是使用 alibaba 开源的 TransmittableThreadLocal 对 InheritableThreadLocal 进行封装，将信息放到 InheritableThreadLocal 里面
 * 这步走完后请求就转发到了我们具体的接口上面，然后这个接口中就会用Feign去调用B服务的接口
 * 所以接下来需要用Feign的拦截器，将刚刚获取的用户ID重新传递到B服务中
 */
public class HttpHeaderParamFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String uid = request.getHeader("uid");
        RibbonFilterContextHolder.getCurrentContext().add("uid", uid);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
