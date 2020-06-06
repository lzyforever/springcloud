package com.jack.sharding.service;

import java.util.List;

import com.jack.sharding.po.User;

public interface UserService {

	List<User> list();
	
	Long add(User user);
	
}
