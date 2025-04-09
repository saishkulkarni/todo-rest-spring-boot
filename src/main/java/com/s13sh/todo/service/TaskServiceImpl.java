package com.s13sh.todo.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s13sh.todo.dto.TaskRequest;
import com.s13sh.todo.entity.Session;
import com.s13sh.todo.entity.Task;
import com.s13sh.todo.exception.InvalidSessionException;
import com.s13sh.todo.exception.NotAllowedException;
import com.s13sh.todo.exception.ResourceNotFound;
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
			Task task = new Task(request, userSession.getUserId());
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

	@Override
	public Map<String, Object> fethcAlltasks(String sessionId) {
		if (checkSession(sessionId)) {
			Session userSession = sessionRepository.findBySessionId(sessionId);
			System.err.println(userSession.getUserId());
			List<Task> tasks = taskRepository.findByUserId(userSession.getUserId());
			if (!tasks.isEmpty()) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("message", "Task Found Success");
				map.put("data", tasks);
				return map;
			}
			throw new ResourceNotFound("No Records Present");
		}
		throw new InvalidSessionException();
	}

	@Override
	public Map<String, Object> fetchTaskById(String sessionId, Long id) {
		if (checkSession(sessionId)) {
			Session userSession = sessionRepository.findBySessionId(sessionId);
			Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFound("No Task from Id :" + id));

			if (task.getUserId() == userSession.getUserId()) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("message", "Task Found Success");
				map.put("data", task);
				return map;
			}
			throw new NotAllowedException("You can See Task only that You have added");
		}
		throw new InvalidSessionException();
	}

	@Override
	public Map<String, Object> deleteTaskById(String sessionId, Long id) {
		if (checkSession(sessionId)) {
			Session userSession = sessionRepository.findBySessionId(sessionId);
			Task task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFound("No Task from Id :" + id));

			if (task.getUserId() == userSession.getUserId()) {
				taskRepository.deleteById(id);
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("message", "Task Deleted Success");
				return map;
			}
			throw new NotAllowedException("You can Delete Task only that You have added");
		}
		throw new InvalidSessionException();
	}

	@Override
	public Map<String, Object> updateTask(Task task, String sessionId) {
		if (checkSession(sessionId)) {
			Session userSession = sessionRepository.findBySessionId(sessionId);
			Task task1 = taskRepository.findById(task.getId())
					.orElseThrow(() -> new ResourceNotFound("No Task from Id :" + task.getId()));

			if (task.getUserId() == userSession.getUserId()) {
				taskRepository.save(task);
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("message", "Task Update Success");
				map.put("data", task);
				return map;
			}
			throw new NotAllowedException("You can Delete Task only that You have added");
		}
		throw new InvalidSessionException();
	}

}
