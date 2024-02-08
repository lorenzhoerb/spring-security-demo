package com.lorenzhoerb.securitydemo.controller;

import com.lorenzhoerb.securitydemo.dto.UserCreateRequest;
import com.lorenzhoerb.securitydemo.dto.UserDetailsDto;
import com.lorenzhoerb.securitydemo.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public String adminSecured() {
        return "Hello from admin";
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDetailsDto createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return adminService.createUser(userCreateRequest);
    }
}
