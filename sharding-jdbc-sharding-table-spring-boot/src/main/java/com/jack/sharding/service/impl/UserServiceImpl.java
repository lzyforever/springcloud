package com.jack.sharding.service.impl;

import com.jack.sharding.po.User;
import com.jack.sharding.repository.UserRepository;
import com.jack.sharding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Long addUser(User user) {
        return userRepository.addUser(user);
    }

    @Override
    public List<User> list() {
        return userRepository.list();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
}
