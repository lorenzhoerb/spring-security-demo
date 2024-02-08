package com.lorenzhoerb.securitydemo.dto;

import jakarta.validation.constraints.NotBlank;

public record PostCreateRequest(
        @NotBlank(message = "Title cannot be blank")
        String title,
        @NotBlank(message = "Body cannot be blank")
        String body
) {
}
