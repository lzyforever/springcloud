package com.jack.sharding.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jack.sharding.po.User;

@Mapper
public interface UserRepository {

    Long addUser(User user);

    List<User> list();

}
