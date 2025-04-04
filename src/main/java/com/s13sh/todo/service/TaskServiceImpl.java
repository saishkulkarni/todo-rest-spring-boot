package com.s13sh.todo.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s13sh.todo.dto.TaskRequest;
import com.s13sh.todo.entity.Session;
import com.s13sh.todo.entity.Task;
import com.s13sh.todo.exception.InvalidSessionException;
import com.s13sh.todo.helper.SessionStatus;
import com.s13sh.todo.repository.SessionRepository;
import com.s13sh.todo.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	SessionRepository sessionRepository;

	@Override
	public Map<String, Object> addTask(TaskRequest request, String sessionId) {
		if (checkSession(sessionId)) {
			Session userSession = sessionRepository.findBySessionId(sessionId);
			Task task = new Task(request, userSession.getUser_id());
			taskRepository.save(task);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("message", "Task Added Success");
			map.put("data", task);
			return map;
		}
		throw new InvalidSessionException();
	}

	public boolean checkSession(String sessionId) {
		if (sessionId != null) {
			Session userSession = sessionRepository.findBySessionId(sessionId);
			if (userSession != null)
				if (userSession.getStatus() == SessionStatus.ACTIVE)
					return true;
		}
		return false;
	}
}
