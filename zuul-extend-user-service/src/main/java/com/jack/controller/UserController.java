package com.jack.controller;

import com.jack.jwt.common.ResponseData;
import com.jack.param.UserParam;
import com.jack.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @ApiOperation(value = "用户登录", notes = "用户登录的校验功能接口")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = ResponseData.class)
    })
    @PostMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseData login(@ApiParam(value = "用户参数", required = true) @RequestBody UserParam param){
        if (param == null || StringUtils.isEmpty(param.getName()) || StringUtils.isEmpty(param.getPwd())) {
            return ResponseData.failByParam("用户名或密码不能为空");
        }
        return ResponseData.ok(userService.login(param.getName(), param.getPwd()));
    }

    @GetMapping(value = "/hello")
    public String hello() {
        System.err.println("用户ID：" + request.getHeader("uid"));
        return "hello";
    }

}
