package com.s13sh.todo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.s13sh.todo.exception.UserExistsException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handle(MethodArgumentNotValidException exception) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("message", exception.getFieldError().getDefaultMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	}

	@ExceptionHandler(UserExistsException.class)
	public ResponseEntity<Map<String, String>> handle(UserExistsException exception) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("message", exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	}
}
