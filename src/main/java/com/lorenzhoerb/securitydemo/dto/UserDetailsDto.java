package com.lorenzhoerb.securitydemo.dto;

public record UserDetailsDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String role
) {
}
