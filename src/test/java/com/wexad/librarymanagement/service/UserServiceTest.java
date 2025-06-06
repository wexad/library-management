package com.wexad.librarymanagement.service;

import com.wexad.librarymanagement.dto.UserDTO;
import com.wexad.librarymanagement.entity.User;
import com.wexad.librarymanagement.mapper.UserMapper;
import com.wexad.librarymanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDTO testUserDTO;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .name("John")
                .email("john@example.com")
                .password("secret")
                .role("ADMIN")
                .build();

        testUserDTO = new UserDTO();
        testUserDTO.setName("John");
        testUserDTO.setEmail("john@example.com");
        testUserDTO.setRole("ADMIN");
    }

    @Test
    void testGetAll() {
        List<User> users = List.of(testUser);
        List<UserDTO> userDTOs = List.of(testUserDTO);

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDtoList(users)).thenReturn(userDTOs);

        List<UserDTO> result = userService.getAll();

        assertEquals(1, result.size());
        verify(userRepository).findAll();
        verify(userMapper).toDtoList(users);
    }

    @Test
    void testRegisterUser_SavesUser_WhenEmailNotExists() {
        String email = "newuser@example.com";
        String name = "New User";
        String password = "123";
        String encodedPassword = "encoded123";
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        userService.registerUser(email, name, password);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals(email, savedUser.getEmail());
        assertEquals(name, savedUser.getName());
        assertEquals(encodedPassword, savedUser.getPassword());
        assertEquals("USER", savedUser.getRole());
        assertNotNull(savedUser.getCreatedAt());
    }

    @Test
    void testRegisterUser_DoesNothing_WhenEmailExists() {
        String email = "existing@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        userService.registerUser(email, "Name", "pass");

        verify(userRepository, never()).save(any(User.class));
    }
}