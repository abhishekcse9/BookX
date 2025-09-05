package com.bookx.service;

import org.springframework.http.ResponseEntity;

import com.bookx.dto.LoginRequest;
import com.bookx.dto.RegisterRequest;

public interface UserService {

	public ResponseEntity<?> register(RegisterRequest request);
	
	public ResponseEntity<?> login(LoginRequest request);
	
	public ResponseEntity<?> getCurrentUser(String token);
	
	public ResponseEntity<?> validateUserAccess(String authHeader, Integer userId);
}
