package com.wexad.librarymanagement.mapper;

import com.wexad.librarymanagement.dto.UserDTO;
import com.wexad.librarymanagement.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDTO, User> {
    @Mapping(source = "role", target = "role")
    @Override
    UserDTO toDto(User entity);
}
