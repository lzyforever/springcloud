package com.jack.sharding.controller;

import com.jack.sharding.po.LouDong;
import com.jack.sharding.service.LouDongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loudong")
public class LouDongController {
    @Autowired
    private LouDongService louDongService;

    @GetMapping("/list")
    public Object list() {
        return louDongService.list();
    }

    @GetMapping("/add")
    public Object add() {
        for (long i = 0; i < 100; i ++) {
            LouDong louDong = new LouDong();
            louDong.setId("ABC" + i);
            louDong.setCity("成都");
            louDong.setRegion("武候");
            louDong.setName("小宝");
            louDong.setLdNum("A");
            louDong.setUnitNum("2");
            louDongService.addLouDong(louDong);
        }
        return "success";
    }
}
