package com.wexad.librarymanagement.util;

import com.wexad.librarymanagement.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class SessionUser {

    private final UserService userService;

    public SessionUser(@Lazy UserService userService) {
        this.userService = userService;
    }

    public Integer getUserId() {
        return userService.getUserIdWithEmail(getUsername());
    }

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return authentication.getName();
    }
}