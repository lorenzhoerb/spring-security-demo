package com.lorenzhoerb.securitydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/moderator")
public class ModeratorController {

    @GetMapping
    public String moderatorSecured() {
        return "Hello from moderator";
    }

}
