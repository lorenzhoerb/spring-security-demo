package com.lorenzhoerb.securitydemo.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateRequest(
        @NotBlank(message = "Text must not be blank")
        String text
) {
}
