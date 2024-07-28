package com.example.AuthService.controller;

import com.example.AuthService.model.dto.LoginDTO;
import com.example.AuthService.model.dto.TokenDTO;
import com.example.AuthService.model.dto.UserDTO;
import com.example.AuthService.model.entity.User;
import com.example.AuthService.services.AuthService;
import com.example.AuthService.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<User>  createUser(@RequestBody @Valid UserDTO dto) {
        if (this.authService.getUserByLogin(dto.login()) != null) return ResponseEntity
                .badRequest().build();

        String bcryptPassword = new BCryptPasswordEncoder().encode(dto.password());
        var user = new User(dto.login(), bcryptPassword, dto.role());
        this.authService.createUser(user);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok().body(new TokenDTO(token));
    }
}
