package com.s13sh.todo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.s13sh.todo.dto.UserRequest;
import com.s13sh.todo.helper.AES;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(unique = true, nullable = false)
	private String email;
	@CreationTimestamp
	private LocalDateTime createdTime;

	public User(UserRequest request) {
		this.email = request.getEmail();
		this.password = AES.encrypt(request.getPassword());
		this.username = request.getUsername();
	}
}
