package com.jack.exception;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义异常处理类
 */
public class GlobalExceptionHandler extends DefaultErrorWebExceptionHandler {

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 获取异常属性
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        int code = 500;
        Throwable error = super.getError(request);
        if (error instanceof org.springframework.cloud.gateway.support.NotFoundException) {
            code = 404;
        }
        return response(code, this.buildMsg(request, error));
    }

    /**
     * 指定响应处理方法为JSON处理的方法
     * 默认的是以HTML来响应,这里指定 renderErrorResponse,它就是返回JSON格式的
     * 在源代码 DefaultErrorWebExceptionHandler 中定义的
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 根据code获取对应的HttpStatus
     * 源代码里面是要取status这个key的,如果没有status就会报错
     * 这里重写覆盖,使用code来替换status
     */
    @Override
    protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
        int statusCode = (int) errorAttributes.get("code");
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 构建异常信息
     * @param request 请求对象
     * @param ex 异常对象
     * @return 构建的异常信息字符串
     */
    private String buildMsg(ServerRequest request, Throwable ex) {
        StringBuilder builder = new StringBuilder("处理请求错误!!  [ 请求方法: ");
        builder.append(request.methodName());
        builder.append(" uri地址: ");
        builder.append(request.uri());
        builder.append(" ]");
        if (ex != null) {
            builder.append(" 异常信息: ");
            builder.append(ex.getMessage());
        }
        return builder.toString();
    }

    /**
     * 构建返回的JSON数据格式
     * @param status 状态码
     * @param msg 异常信息
     * @return 响应对象
     */
    public static Map<String, Object> response(int status, String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", status);
        map.put("message", msg);
        map.put("data", null);
        return map;
    }
}
