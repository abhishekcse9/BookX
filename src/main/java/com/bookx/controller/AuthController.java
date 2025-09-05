package com.bookx.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookx.dto.LoginRequest;
import com.bookx.dto.RegisterRequest;
import com.bookx.serviceImpl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<?> registerMethod(@RequestBody RegisterRequest request) {
        ResponseEntity<?> register = userServiceImpl.register(request);
        return register;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMethod(@RequestBody LoginRequest request) {
        ResponseEntity<?> login = userServiceImpl.login(request);
        return login;
    }

    @GetMapping("/getCurrentUser")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCurrentUserMethod(@RequestHeader("Authorization") String token) {
        ResponseEntity<?> currentUser = userServiceImpl.getCurrentUser(token);
        return currentUser;
    }
    

    @GetMapping("/status")
    public ResponseEntity<?> getAuthStatus(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("SPRING_SECURITY_CONTEXT") != null) {
            return ResponseEntity.ok().body(Map.of("authenticated", true));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("authenticated", false));
    }

}




