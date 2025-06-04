package com.wexad.librarymanagement.mapper;


import com.wexad.librarymanagement.dto.UserDTO;
import com.wexad.librarymanagement.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testToDto() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("secret");
        user.setRole("ADMIN");
        UserDTO dto = userMapper.toDto(user);
        assertNotNull(dto);
        assertEquals("John Doe", dto.getName());
        assertEquals("john@example.com", dto.getEmail());
        assertEquals("ADMIN", dto.getRole());
    }

    @Test
    void testToEntity() {
        UserDTO dto = new UserDTO();
        dto.setName("Jane Doe");
        dto.setEmail("jane@example.com");
        dto.setRole("USER");
        User user = userMapper.toEntity(dto);
        assertNotNull(user);
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("USER", user.getRole());
        assertNull(user.getPassword());
    }
}
