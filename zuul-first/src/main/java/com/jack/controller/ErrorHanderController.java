package com.jack.controller;

import com.jack.vo.ResponseCode;
import com.jack.vo.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 后端的接口都是REST风格的API,返回的数据都是有固定格式的JSON格式，如果出现了
 * 像500、404这些页面，客户端是无法处理的，所以就需要一个错误处理的Controller来处理
 *
 * 为什么要单独定义一个Controller来处理异常喃？
 * 因为SpringBoot中统一进行异常处理，我们也是将错误转成JSON返回调用方，但是@ControllerAdvice
 * 注解主要用来针对Controller中的方法做处理，作用于@RequestMapping标注的方法上，只能对定义的
 * 接口异常有效，在Zuul中是无效的，所以需要这么做才得行。。。
 *
 */
@RestController
public class ErrorHanderController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public ResponseData error(HttpServletRequest request) {
        Map<String, Object> errors = getErrorAttributes(request);
        String message = (String) errors.get("message");
        String trace = (String) errors.get("trace");
        if (StringUtils.isNotBlank(trace)) {
            message += String.format(" and trace %s", trace);
        }
        return ResponseData.fail(message, ResponseCode.SERVER_ERROR_CODE.getCode());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
        return errorAttributes.getErrorAttributes(new ServletWebRequest(request), true);
    }

}
