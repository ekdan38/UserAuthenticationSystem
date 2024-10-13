package com.example.userauthenticationsystem.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Account {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String roles;
    private int age;
}
