package com.jack.sharding.service.impl;

import com.jack.jdbc.EntityService;
import com.jack.sharding.entity.User;
import com.jack.sharding.service.UserService;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends EntityService<User> implements UserService {
    @Override
    public List<User> list() {
        // Hint强制路由主库，从主库查询数据
        // HintManager.getInstance().setMasterRouteOnly();
        return super.list();
    }

    @Override
    public Long add(User user) {
        // 默认是从从库查询数据
        return (Long) super.save(user);
    }
}
