package com.lorenzhoerb.securitydemo.service;

import com.lorenzhoerb.securitydemo.dto.AuthenticationResponse;
import com.lorenzhoerb.securitydemo.dto.LoginRequest;
import com.lorenzhoerb.securitydemo.dto.SignUpRequest;
import com.lorenzhoerb.securitydemo.model.Role;
import com.lorenzhoerb.securitydemo.model.User;
import com.lorenzhoerb.securitydemo.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        var authentication = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        authenticationManager.authenticate(authentication);
        var user = userRepository.findByEmail(loginRequest.email()).orElseThrow();
        return getAuthenticationResponse(user);
    }

    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userAlreadyExists(signUpRequest)) {
            throw new DuplicateKeyException("User with this email already exists");
        }

        var user = new User();
        final var encodedPassword = passwordEncoder.encode(signUpRequest.password());
        user.setEmail(signUpRequest.email());
        user.setPassword(encodedPassword);
        user.setFirstName(signUpRequest.firstName());
        user.setLastName(signUpRequest.lastName());
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);

        return getAuthenticationResponse(user);
    }

    private boolean userAlreadyExists(SignUpRequest signUpRequest) {
        return userRepository.findByEmail(signUpRequest.email()).isPresent();
    }

    private AuthenticationResponse getAuthenticationResponse(User user) {
        return new AuthenticationResponse(jwtService.generateToken(user));
    }

}
