package com.example.userauthenticationsystem.domain.service;

import com.example.userauthenticationsystem.domain.entity.Account;
import com.example.userauthenticationsystem.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public Account createUser(Account account){
        return userRepository.save(account);
    }


}
