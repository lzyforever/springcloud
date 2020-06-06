package com.jack.exception;

import com.jack.entity.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData defaultErrorHandler(HttpServletRequest req, Exception ex) {
        logger.error("", ex);
        ResponseData rs = new ResponseData();
        rs.setMessage(ex.getMessage());
        if (ex instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            rs.setCode(404);
        } else {
            rs.setCode(500);
        }
        rs.setData(null);
        rs.setStatus(false);
        return rs;
    }

}
