package com.bookx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getCurrentUserMethod(@RequestHeader("Authorization") String token) {
        ResponseEntity<?> currentUser = userServiceImpl.getCurrentUser(token);
        return currentUser;
    }
    
}




