package com.s13sh.todo.service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s13sh.todo.dto.UserRequest;
import com.s13sh.todo.entity.Session;
import com.s13sh.todo.entity.User;
import com.s13sh.todo.exception.InvalidException;
import com.s13sh.todo.exception.InvalidSessionException;
import com.s13sh.todo.exception.UserExistsException;
import com.s13sh.todo.helper.AES;
import com.s13sh.todo.helper.SessionStatus;
import com.s13sh.todo.repository.SessionRepository;
import com.s13sh.todo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	SessionRepository sessionRepository;

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

	@Override
	public Map<String, String> login(UserRequest request, HttpSession session) {
		if(!sessionRepository.existsBySessionId(session.getId())) {
		Optional<User> user = userRepository.findByUsername(request.getUsername());
		if (user.isPresent()) {
			if (request.getPassword().equals(AES.decrypt(user.get().getPassword()))) {
				
				Session userSession = new Session();
				userSession.setSessionId(session.getId());
				userSession.setUserId(user.get().getId());
				userSession.setStatus(SessionStatus.ACTIVE);
				sessionRepository.save(userSession);

				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("message", "User Login Success");
				map.put("sessionId", session.getId());
				return map;
			} else
				throw new InvalidException("Invalid Password");
		} else
			throw new InvalidException("Invalid Username");
		}else {
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("message", "Aleady a Session is Active, Logout that to Login again");
			return map;
		}
	}

	@Override
	public Map<String, String> logout(String sessionId, HttpSession session) {
		if (sessionId != null) {
			Session userSession = sessionRepository.findBySessionId(sessionId);
			if (userSession != null) {
				session.invalidate();
				userSession.setStatus(SessionStatus.INVALIDATED);
				userSession.setLoggedOutTime(LocalDateTime.now());
				sessionRepository.save(userSession);
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("message", "Logout Success");
				return map;
			} else {
				throw new InvalidSessionException();
			}
		} else {
			throw new InvalidSessionException();
		}
	}

}
