package com.lorenzhoerb.securitydemo;

import com.lorenzhoerb.securitydemo.model.Role;
import com.lorenzhoerb.securitydemo.model.User;
import com.lorenzhoerb.securitydemo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            User user = new User();
            user.setEmail("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setRole(Role.ROLE_ADMIN);

            userRepository.save(user);
        };
    }
}
