package com.s13sh.todo.exception;

public class UserExistsException extends RuntimeException {
	public UserExistsException(String message) {
		super(message);
	}
}
