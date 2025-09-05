package com.bookx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin(origins = "http://localhost:3000") 
public class AppController {

    @GetMapping("/")
    public String home() {
        return "redirect:http://localhost:3000";
    }

//    @GetMapping("/login")
//    public String login() {
//        return "redirect:http://localhost:3000/login";
//    }
//
//    @GetMapping("/register") 
//    public String register() {
//        return "redirect:http://localhost:3000/register";
//    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:http://localhost:3000/dashboard";
    }

    @GetMapping("/aboutus")
    public String aboutUs() {
        return "redirect:http://localhost:3000/aboutus";
    }
}