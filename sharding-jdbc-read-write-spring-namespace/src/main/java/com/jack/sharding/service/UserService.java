package com.jack.sharding.service;

import com.jack.sharding.entity.User;

import java.util.List;

public interface UserService {
    List<User> list();

    Long add(User user);
}
