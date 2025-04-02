package com.s13sh.todo.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s13sh.todo.dto.UserRequest;
import com.s13sh.todo.entity.User;
import com.s13sh.todo.exception.UserExistsException;
import com.s13sh.todo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public Map<String, String> registerUser(UserRequest request) {
		if (userRepository.existsByEmail(request.getEmail()))
			throw new UserExistsException("Email Already Exists");
		if (userRepository.existsByUsername(request.getUsername()))
			throw new UserExistsException("Username Already Exists");
		
		User user = new User(request);
		userRepository.save(user);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("message", "User Registered Successfully");
		return map;
	}

}
