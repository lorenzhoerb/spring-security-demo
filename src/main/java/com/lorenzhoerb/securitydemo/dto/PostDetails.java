package com.lorenzhoerb.securitydemo.dto;

import java.time.LocalDateTime;

public record PostDetails(
        Long id,
        String title,
        String body,
        AuthorDetails author,
        LocalDateTime dateCreated,
        LocalDateTime dateUpdated
) {

}
