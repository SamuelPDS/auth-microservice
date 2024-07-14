package com.example.AuthService.model.dto;

import com.example.AuthService.util.UserRole;

public record UserDTO(String login, String password, UserRole role) {
}
