package com.example.userauthenticationsystem;


import com.example.userauthenticationsystem.domain.entity.Account;
import com.example.userauthenticationsystem.web.dto.AccountDto;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account toEntity(AccountDto dto) {
        return Account.createAccount(
                dto.getUsername(),
                dto.getPassword(),
                dto.getRoles(),
                dto.getAge());
    }

}
