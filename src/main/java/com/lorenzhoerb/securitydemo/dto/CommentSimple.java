package com.lorenzhoerb.securitydemo.dto;

public record CommentSimple(
        Long id,
        Long authorId,
        Long postId,
        String text
) {
}
