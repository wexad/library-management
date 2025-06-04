package com.wexad.librarymanagement.mapper;

import com.wexad.librarymanagement.dto.ReservationDTO;
import com.wexad.librarymanagement.entity.Book;
import com.wexad.librarymanagement.entity.Reservation;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationMapperTest {

    private final ReservationMapper reservationMapper = Mappers.getMapper(ReservationMapper.class);

    @Test
    void testToDto() {
        Book book = new Book();
        book.setTitle("Effective Java");
        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setStatus("reserved");
        reservation.setDuration(7);
        reservation.setCreatedAt(LocalDateTime.of(2025, 6, 4, 10, 0));
        ReservationDTO dto = reservationMapper.toDto(reservation);
        assertNotNull(dto);
        assertEquals("Effective Java", dto.getName());
        assertEquals("reserved", dto.getStatus());
        assertEquals(LocalDateTime.of(2025, 6, 11, 10, 0), dto.getDueDate());
    }
}