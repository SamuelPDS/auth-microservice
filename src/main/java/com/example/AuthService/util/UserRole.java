package com.example.AuthService.util;

public enum UserRole {
    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    INSPECTOR("INSPECTOR");

    final String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
