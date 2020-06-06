package com.jack.sharding.service;

import com.jack.sharding.po.LouDong;

import java.util.List;

public interface LouDongService {
    Long addLouDong(LouDong louDong);

    List<LouDong> list();
}
