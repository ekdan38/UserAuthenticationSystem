package com.example.userauthenticationsystem.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestLoginController {

    @PostMapping("/api/login")
    public String login(){
        return "apiLogin";
    }
}
