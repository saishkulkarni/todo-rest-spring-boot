package com.s13sh.todo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
	@Size(min = 3, max = 20, message = "Task Name Should be 3~20 charecters")
	private String name;
	@Size(min = 10, max = 50, message = "Task Description Should be 10~50 charecters")
	private String description;
	@NotNull(message = "Status is Required")
	private String status;
}
