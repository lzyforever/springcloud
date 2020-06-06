package com.jack.sharding.service.impl;

import com.jack.jdbc.EntityService;
import com.jack.sharding.entity.LouDong;
import com.jack.sharding.service.LouDongService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LouDongServiceImpl extends EntityService<LouDong> implements LouDongService {

    @Override
    public Long addLouDong(LouDong louDong) {
        return (Long) super.save(louDong);
    }

    @Override
    public List<LouDong> list() {
        return super.list();
    }
}
