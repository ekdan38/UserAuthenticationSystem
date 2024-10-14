package com.example.userauthenticationsystem.web.controller;

import com.example.userauthenticationsystem.security.userdetails.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal CustomUserDetails details) {
        String roles = details.getAccountDto().getRoles();
        log.info("role = {}",roles);
        Collection<? extends GrantedAuthority> authorities = details.getAuthorities();
        Object[] array = authorities.toArray();
        for (Object o : array) {
            log.info("authorities = " + o);
        }
        return "user";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }


    @GetMapping("/api")
    public String restdashboard() {
        return "rest/dashboard";
    }
}
