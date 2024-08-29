package com.example.AuthService.model.entity;

import com.example.AuthService.model.dto.UserDTO;
import com.example.AuthService.util.UserRole;
import com.example.AuthService.util.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 14)
    private String login;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(length = 24)
    private UserStatus status;


    public User(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        //TODO Definir como a role será criada
        this.role = role;
        status = UserStatus.ENABLED;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == UserStatus.ENABLED;
    }
}
