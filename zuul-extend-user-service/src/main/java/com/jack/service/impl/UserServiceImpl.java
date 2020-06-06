package com.jack.service.impl;

import com.jack.jwt.utils.JWTUtils;
import com.jack.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String login(String name, String pwd) {
        JWTUtils jwtUtils = JWTUtils.getInstance();
        if ("admin".equals(name) && "123456".equals(pwd)) {
            return jwtUtils.getToken(name);
        }
        return "";
    }
}
