package com.s13sh.todo.service;

import java.util.Map;

import com.s13sh.todo.dto.TaskRequest;

import jakarta.validation.Valid;

public interface TaskService {

	Map<String, Object> addTask(@Valid TaskRequest request, String sessionId);

}
