package com.example.AuthService.model.dto;

import com.example.AuthService.util.UserRole;
import com.example.AuthService.util.UserStatus;

public record UserDTO(String login, String password, UserRole role, UserStatus status) {
}
