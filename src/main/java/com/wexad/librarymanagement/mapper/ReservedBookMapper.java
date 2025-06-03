package com.wexad.librarymanagement.mapper;

import com.wexad.librarymanagement.dto.ReservedBookDTO;
import com.wexad.librarymanagement.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservedBookMapper extends BaseMapper<ReservedBookDTO, Reservation> {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.name", target = "username")
    @Mapping(source = "book.title", target = "bookTitle")
    @Override
    ReservedBookDTO toDto(Reservation entity);
}
