package com.jack.sharding.service;

import com.jack.sharding.po.User;

import java.util.List;

public interface UserService {
    Long addUser(User user);

    List<User> list();

    User findById(Long id);

    User findByName(String name);
}
