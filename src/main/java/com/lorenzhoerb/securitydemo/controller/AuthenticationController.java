package com.lorenzhoerb.securitydemo.controller;

import com.lorenzhoerb.securitydemo.dto.AuthenticationResponse;
import com.lorenzhoerb.securitydemo.dto.LoginRequest;
import com.lorenzhoerb.securitydemo.dto.SignUpRequest;
import com.lorenzhoerb.securitydemo.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticationResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authenticationService.signUp(signUpRequest);
    }
}
