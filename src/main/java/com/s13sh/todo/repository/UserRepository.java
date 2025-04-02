package com.s13sh.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.todo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);

	boolean existsByUsername(String username);
}
