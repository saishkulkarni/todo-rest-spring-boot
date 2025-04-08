package com.s13sh.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.s13sh.todo.entity.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

	Session findBySessionId(String sessionId);

	boolean existsBySessionId(String id);

}
