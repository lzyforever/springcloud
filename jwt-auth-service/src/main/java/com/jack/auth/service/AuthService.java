package com.jack.auth.service;

import com.jack.auth.pojo.User;
import com.jack.auth.query.AuthQuery;

/**
 * 认证服务接口
 */
public interface AuthService {
    User auth(AuthQuery query);
}
