package com.example.userauthenticationsystem.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String dashboard() {
        return "login/dashboard";
    }

    @GetMapping("/user")
    public String user() {
        return "login/user";
    }

    @GetMapping("/manager")
    public String manager() {
        return "login/manager";
    }

    @GetMapping("/admin")
    public String admin() {
        return "login/admin";
    }
}
