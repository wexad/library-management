package com.wexad.librarymanagement.service;

import com.wexad.librarymanagement.dto.UserDTO;
import com.wexad.librarymanagement.entity.User;
import com.wexad.librarymanagement.mapper.UserMapper;
import com.wexad.librarymanagement.repository.UserRepository;
import com.wexad.librarymanagement.util.SessionUser;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SessionUser sessionUser;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, SessionUser sessionUser, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.sessionUser = sessionUser;
        this.userMapper = userMapper;
    }

    public UserDTO getUser() {
        return userMapper.toDto(userRepository.getUsersById(sessionUser.getUserId()));
    }
}
