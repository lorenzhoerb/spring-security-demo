package com.lorenzhoerb.securitydemo.security;

import com.lorenzhoerb.securitydemo.dto.UserCreateRequest;
import com.lorenzhoerb.securitydemo.dto.UserDetailsDto;
import com.lorenzhoerb.securitydemo.model.User;
import com.lorenzhoerb.securitydemo.repository.UserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetailsDto createUser(UserCreateRequest userCreateRequest) {
        if (userAlreadyExists(userCreateRequest)) {
            throw new DuplicateKeyException("User with email " + userCreateRequest.email() + " already exists");
        }

        var user = new User();
        user.setEmail(userCreateRequest.email());
        user.setFirstName(userCreateRequest.firstName());
        user.setLastName(userCreateRequest.lastName());
        user.setRole(userCreateRequest.role());
        user.setPassword(passwordEncoder.encode(userCreateRequest.password()));

        user = userRepository.save(user);

        return new UserDetailsDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name()
        );
    }

    private boolean userAlreadyExists(UserCreateRequest userCreateRequest) {
        return userRepository.findByEmail(userCreateRequest.email()).isPresent();
    }
}
