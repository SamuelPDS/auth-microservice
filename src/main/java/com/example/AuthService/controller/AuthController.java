package com.example.AuthService.controller;

import com.example.AuthService.model.dto.LoginDTO;
import com.example.AuthService.model.dto.TokenDTO;
import com.example.AuthService.model.dto.UserDTO;
import com.example.AuthService.model.entity.User;
import com.example.AuthService.services.AuthService;
import com.example.AuthService.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "AuthService")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Cria o usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BadRequest"),

    })
    @PostMapping(value = "/register")
    public ResponseEntity<User>  createUser(@RequestBody @Valid UserDTO dto) {
        try {
            String bcryptPassword = new BCryptPasswordEncoder().encode(dto.password());
            var user = new User(dto.login(), bcryptPassword, dto.role());
            this.authService.createUser(user);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
//
//
//        if (this.authService.getUserByLogin(dto.login()) != null) return ResponseEntity
//                .badRequest().build();

    }

    @Operation(summary = "Verifica o login", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "forbidden")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok().body(new TokenDTO(token));
    }
}
