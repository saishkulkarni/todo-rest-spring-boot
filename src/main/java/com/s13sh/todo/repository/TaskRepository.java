package com.s13sh.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.todo.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}
