package com.s13sh.todo.service;

import java.util.Map;

import com.s13sh.todo.dto.UserRequest;

public interface UserService {
	Map<String, String> registerUser(UserRequest request);
}
