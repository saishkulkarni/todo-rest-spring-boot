package com.s13sh.todo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.s13sh.todo.dto.TaskRequest;
import com.s13sh.todo.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;

	@PostMapping
	public ResponseEntity<Map<String, Object>> addTask(@RequestBody @Valid TaskRequest request,
			@RequestHeader(required = false) String sessionId) {
		return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addTask(request, sessionId));
	}
}
