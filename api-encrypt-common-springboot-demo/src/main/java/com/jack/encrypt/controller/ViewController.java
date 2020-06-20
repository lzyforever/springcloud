package com.jack.encrypt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/index")
    public String view() {
        return "index";
    }

    @GetMapping("/index2")
    public String view2() {
        return "index2";
    }

    @GetMapping("/rsa")
    public String view3() {
        return "RSA";
    }
}
