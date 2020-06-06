package com.jack.filter;

import brave.Span;
import brave.Tracer;
import org.springframework.cloud.sleuth.instrument.web.TraceWebServletAutoConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TracingFlter的使用
 * 给请求添加自定义的标记以及将请求ID添加到响应头返回给客户端
 */
@Component
@Order(TraceWebServletAutoConfiguration.TRACING_FILTER_ORDER + 1)
public class MyFilter extends GenericFilterBean {

    private final Tracer tracer;

    public MyFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Span currentSpan = this.tracer.currentSpan();
        if (null == currentSpan) {
            chain.doFilter(request, response);
            return;
        }
        // 手动设置的请求ID，可以通过查看请求的响应信息来查看
        ((HttpServletResponse) response).addHeader("ZIPKIN-TRACE-ID", currentSpan.context().traceIdString());
        // 手动创建的标记可以在Zipkin上面查看
        currentSpan.tag("custom", "tag");
        chain.doFilter(request, response);
    }
}