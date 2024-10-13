package com.example.userauthenticationsystem.web.controller;

import com.example.userauthenticationsystem.AccountMapper;
import com.example.userauthenticationsystem.domain.entity.Account;
import com.example.userauthenticationsystem.domain.service.UserService;
import com.example.userauthenticationsystem.web.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PostMapping("/signup")
    public String signup(@ModelAttribute AccountDto accountDto) {
        Account account = accountMapper.toEntity(accountDto);
        account.setEncodePassword(passwordEncoder.encode(account.getPassword()));
        userService.createUser(account);
        return "redirect:/";
    }
}
