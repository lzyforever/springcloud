package com.jack.filter;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.cloud.netflix.ribbon.RibbonHttpResponse;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;

/**
 * 记录请求响应信息输出日志信息过滤器
 */
public class DebugRequestFiliter extends ZuulFilter {

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        // 记录请求格式（http、https等）、IP地址、端口
        System.out.println("Request：> " + request.getScheme() + " " + request.getRemoteAddr() + " : " + request.getRemotePort());
        StringBuilder params = new StringBuilder("?");
        // 获取URL参数
        Enumeration<String> names = request.getParameterNames();
        if (request.getMethod().equals("GET")) {
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                params.append(name);
                params.append("=");
                params.append(request.getParameter(name));
                params.append("&");
            }
        }
        // 删除最后一个&符号
        if (params.length() > 0) {
            params.delete(params.length() - 1, params.length());
        }

        // 记录请求方式、请求URI地址和参数、请求协议
        System.out.println("Request：> " + request.getMethod() + " " +request.getRequestURI() + params + " " + request.getProtocol());

        // 获取Header信息，并打印出来
        Enumeration<String> requestHeaders = request.getHeaderNames();
        while (requestHeaders.hasMoreElements()) {
            String key = requestHeaders.nextElement();
            String value = request.getHeader(key);
            System.out.println("Request Header：> " + key + " : " + value);
        }

        // 获取请求体参数，并打印出来
        if (ctx.isChunkedRequestBody()) {
            ServletInputStream ins = null;
            try {
                ins = request.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String body = IOUtils.toString(ins);
            System.out.println("Request body：> " + body);
        }

        // 获取响应头信息，并打印出来
        List<Pair<String, String>> responseHeaders = ctx.getOriginResponseHeaders();
        for (Pair<String, String> pair : responseHeaders) {
            System.out.println("Response header：> " + pair.second());
        }

        // 第一种方式：获取响应结果信息，并打印出来
//        Object zuulResponse = ctx.get("zuulResponse");
//        if (zuulResponse != null) {
//            RibbonHttpResponse resp = (RibbonHttpResponse) zuulResponse;
//            try {
//                String body = IOUtils.toString(resp.getBody());
//                System.out.println("Response body：> " + body);
//                // 记得要关闭和设置响应内容，不然后台会报错，前端无响应
//                resp.close();
//                ctx.setResponseBody(body);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        // 第二种方式：获取响应结果信息，并打印出来
        InputStream stream = ctx.getResponseDataStream();
        if (stream != null) {
            String body = IOUtils.toString(stream);
            System.out.println("Response body：> " + body);
            ctx.setResponseBody(body);
        }

        return null;
    }
}
