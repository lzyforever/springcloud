package com.jack.sharding.service;

import java.util.List;

import com.jack.sharding.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jack.sharding.po.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> list() {
		// 强制路由主库
		//HintManager.getInstance().setMasterRouteOnly();
		return userRepository.list();
	}

	public Long add(User user) {
		return userRepository.addUser(user);
	}

}
