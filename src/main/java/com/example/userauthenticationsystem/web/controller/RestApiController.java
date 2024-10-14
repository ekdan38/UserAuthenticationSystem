package com.example.userauthenticationsystem.web.controller;

import com.example.userauthenticationsystem.security.userdetails.CustomUserDetails;
import com.example.userauthenticationsystem.web.dto.AccountDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @GetMapping("/user")
    public AccountDto restUser(@AuthenticationPrincipal CustomUserDetails userDetails){
        AccountDto accountDto = userDetails.getAccountDto();
        Authentication authentication = SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication();
        boolean authenticated = authentication.isAuthenticated();
        System.out.println("authenticated = " + authenticated);
        return accountDto;
    }

    @GetMapping("/manager")
    public AccountDto restManager(@AuthenticationPrincipal CustomUserDetails userDetails){
        AccountDto accountDto = userDetails.getAccountDto();
        return accountDto;
    }

    @GetMapping("/admin")
    public AccountDto restAdmin(@AuthenticationPrincipal CustomUserDetails userDetails){
        AccountDto accountDto = userDetails.getAccountDto();
        return accountDto;
    }


}
