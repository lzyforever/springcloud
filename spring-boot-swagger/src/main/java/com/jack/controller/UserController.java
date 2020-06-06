package com.jack.controller;

import com.jack.entity.User;
import com.jack.param.UserParam;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"用户接口"})
@RestController
public class UserController {

    @ApiOperation(value = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户ID", dataType = "string", paramType = "query", required = true, defaultValue = "1")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = User.class)
    })
    @GetMapping("/user")
    public User getUser(@RequestParam String uid) {
        return new User();
    }

    @ApiOperation(value = "新增用户", notes = "这里可以描述一下这个接口到底是在搞什么啊，可以写很长的啊。。。")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = User.class)
    })
    @PostMapping("/user")
    public User addUser(@ApiParam(value = "新增用户参数", required = true) @RequestBody UserParam param) {
        System.err.println(param.getUname());
        return new User();
    }
}
