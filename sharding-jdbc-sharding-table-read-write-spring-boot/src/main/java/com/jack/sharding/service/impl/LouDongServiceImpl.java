package com.jack.sharding.service.impl;

import com.jack.sharding.po.LouDong;
import com.jack.sharding.repository.LouDongRepository;
import com.jack.sharding.service.LouDongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LouDongServiceImpl implements LouDongService {

    @Autowired
    private LouDongRepository louDongRepository;

    @Override
    public Long addLouDong(LouDong louDong) {
        return louDongRepository.addLouDong(louDong);
    }

    @Override
    public List<LouDong> list() {
        return louDongRepository.list();
    }
}
