package org.example.spring_security_basic.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @GetMapping("/public/hello")
    public String publicApi() {
        return "Public API";
    }

    @GetMapping("/user/hello")
    public String userApi() {
        return "User API";
    }

    @GetMapping("/admin/hello")
    public String adminApi() {
        return "Admin API";
    }

    @GetMapping("/public/logout-success")
    public String logoutSuccess() {
        return "Logged out successfully";
    }

}
