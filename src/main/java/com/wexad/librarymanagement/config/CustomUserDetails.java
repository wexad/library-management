package com.wexad.librarymanagement.config;

import com.wexad.librarymanagement.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements org.springframework.security.core.userdetails.UserDetails {
    @Getter
    private Long id;
    private User user;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
