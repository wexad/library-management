package com.wexad.librarymanagement.service;

import com.wexad.librarymanagement.dto.UserDTO;
import com.wexad.librarymanagement.entity.User;
import com.wexad.librarymanagement.mapper.UserMapper;
import com.wexad.librarymanagement.repository.UserRepository;
import com.wexad.librarymanagement.util.SessionUser;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SessionUser sessionUser;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, @Lazy SessionUser sessionUser, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.sessionUser = sessionUser;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO getUser() {
        return userMapper.toDto(userRepository.getUsersById(sessionUser.getUserId()));
    }

    public List<UserDTO> getAll() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    public void createAdmin(String name, String email, String password, String role) {
        userRepository.save(
                User.builder()
                        .name(name)
                        .email(email)
                        .password(password)
                        .role(role)
                        .build()
        );
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void registerUser(String email, String name, String password) {
        if (!existsByEmail(email)) {
            userRepository.save(
                    User.builder()
                            .email(email)
                            .name(name)
                            .password(passwordEncoder.encode(password))
                            .role("USER")
                            .createdAt(LocalDateTime.now())
                            .build()
            );
        }
    }

    public Integer getUserIdWithEmail(String email) {
        return userRepository.getIdWithEmail(email);
    }
}
