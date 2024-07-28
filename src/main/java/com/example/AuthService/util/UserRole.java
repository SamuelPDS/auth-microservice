package com.example.AuthService.util;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    INSPECTOR("INSPECTOR");

    final String role;

    UserRole(String role){
        this.role = role;
    }
}
