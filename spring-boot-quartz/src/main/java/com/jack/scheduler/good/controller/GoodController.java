package com.jack.scheduler.good.controller;

import com.jack.scheduler.good.entity.GoodInfo;
import com.jack.scheduler.good.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品控制层
 */
@RestController
@RequestMapping("/good")
public class GoodController {

    /**
     * 商品业务逻辑实现
     */
    @Autowired
    private GoodService goodService;

    /**
     * 添加商品
     */
    @RequestMapping(value = "/save")
    public Long save(@RequestBody GoodInfo goodInfo) throws Exception {
        return goodService.saveGood(goodInfo);
    }
}
