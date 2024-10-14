package com.example.userauthenticationsystem.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String username;
    private String password;
    private int age;
    private String roles;
}
// 실제 프로젝트면 validation 해주자!!!!