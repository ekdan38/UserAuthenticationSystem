package com.example.userauthenticationsystem.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String roles;
    private int age;

    private Account(String username, String password, String roles, int age) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.age = age;
    }

    public static Account createAccount(String username, String password, String roles, int age){
        return new Account(username, password, roles, age);
    }
    public void setEncodePassword(String password){
        this.password = password;
    }
}
