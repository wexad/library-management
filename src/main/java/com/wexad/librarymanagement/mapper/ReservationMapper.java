package com.wexad.librarymanagement.mapper;

import com.wexad.librarymanagement.dto.ReservationDTO;
import com.wexad.librarymanagement.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper extends BaseMapper<ReservationDTO, Reservation> {
    @Mapping(source = "book.title", target = "name")
    @Override
    ReservationDTO toDto(Reservation entity);
}
