package com.jack.auth.service.impl;

import com.jack.auth.pojo.User;
import com.jack.auth.query.AuthQuery;
import com.jack.auth.service.AuthService;
import org.springframework.stereotype.Service;

/**
 * 认证实现类
 */
@Service
public class AuthServiceImpl implements AuthService {
    /**
     * 判断用户是否存在
     * 这里写认证逻辑，此处省略直接返回一个用户，可以从数据库查询，做对比
     */
    @Override
    public User auth(AuthQuery query) {
        return new User(1L);
    }
}
