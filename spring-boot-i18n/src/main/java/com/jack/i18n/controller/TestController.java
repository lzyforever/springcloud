package com.jack.i18n.controller;

import com.alibaba.fastjson.JSONObject;
import com.jack.i18n.common.vo.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 在header中添加 lang
 * 传zh返中文，传en返英文，不传默认返中文
 */
@RestController
public class TestController {

    @RequestMapping("/")
    public Result<JSONObject> test() {
        JSONObject object = new JSONObject();
        object.put("hahah", "abc");
        return Result.success(object);
    }


    @RequestMapping("/success")
    public Result hello() {
        return Result.success();
    }

    @RequestMapping("/fail")
    public Result fail() {
        return Result.fail();
    }



}
