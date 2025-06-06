package com.wexad.librarymanagement.util;

import com.wexad.librarymanagement.config.CustomUserDetails;
import org.springframework.stereotype.Component;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Component
public class SessionUser {

    public Long getUserId() {
        return getUser().getId();
    }

    public CustomUserDetails getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            return customUserDetails;
        }
        return null;
    }
}