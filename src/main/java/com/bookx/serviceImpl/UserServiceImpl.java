package com.bookx.serviceImpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookx.dto.AuthResponse;
import com.bookx.dto.ErrorResponse;
import com.bookx.dto.LoginRequest;
import com.bookx.dto.RegisterRequest;
import com.bookx.entity.User;
import com.bookx.exceptions.UserNotFoundException;
import com.bookx.jwt.JwtUtil;
import com.bookx.repository.UserRepo;
import com.bookx.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	
	public ResponseEntity<?> register(RegisterRequest request) {
		if (userRepo.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body("Email already exits!");
		}
		
		User user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		userRepo.save(user);
		
		String token = jwtUtil.generateToken(user.getEmail());
		return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), "User registered successfully"));
	}
	
	
	public ResponseEntity<?> login(LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElse(null);
        
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token, user.getEmail(), "User loggedin successfully"));
	}	
	
	
	public ResponseEntity<?> getCurrentUser(String token) {
        String jwt = token.replace("Bearer ", "");
        if (!jwtUtil.isTokenValid(jwt)) {
            return ResponseEntity.badRequest().body("Invalid token");
        }
        
        String email = jwtUtil.extractEmail(jwt);
        User user = userRepo.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getId());
        response.put("email", user.getEmail());
        response.put("firstName", user.getFirstName());

        return ResponseEntity.ok(response);
	}
	
	public ResponseEntity<?> validateUserAccess(String authHeader, Integer userId) {
	    String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;

	    if (!jwtUtil.isTokenValid(token)) {
	        ErrorResponse errorResponse = new ErrorResponse(
	                HttpStatus.UNAUTHORIZED.value(),
	                "Unauthorized",
	                "Invalid or expired token",
	                LocalDateTime.now()
	        );
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
	    }

	    String email = jwtUtil.extractEmail(token);

	    User user = userRepo.findByEmail(email)
	            .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

	    if (!user.getId().equals(userId)) {
	        ErrorResponse errorResponse = new ErrorResponse(
	                HttpStatus.FORBIDDEN.value(),
	                "Forbidden",
	                "You cannot perform this action for another user",
	                LocalDateTime.now()
	        );
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
	    }

	    return null; 
	}


}
