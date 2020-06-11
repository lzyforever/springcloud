package com.jack.encrypt.controller;

import com.jack.encrypt.aonnotation.Decrypt;
import com.jack.encrypt.aonnotation.Encrypt;
import com.jack.encrypt.param.StuInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    /**
     * 进入测试页面
     */
    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    /**
     * 请求请求数据
     * @param info
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    @Decrypt
    @Encrypt
    public Object save(@RequestBody StuInfo info) {
        System.out.println(info.getName());
        return info;
    }


    /**
     * 测试返回数据
     * @return
     */
    @GetMapping("/get")
    @ResponseBody
    @Encrypt
    public Object get() {
        StuInfo stuInfo = new StuInfo();
        stuInfo.setName("jack luo");
        return stuInfo;
    }

}
