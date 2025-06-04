package com.wexad.librarymanagement.mapper;

import com.wexad.librarymanagement.dto.ReservedBookDTO;
import com.wexad.librarymanagement.entity.Book;
import com.wexad.librarymanagement.entity.Reservation;
import com.wexad.librarymanagement.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservedBookMapperTest {

    private final ReservedBookMapper mapper = Mappers.getMapper(ReservedBookMapper.class);

    @Test
    void testToDto() {
        User user = new User();
        user.setName("John Doe");
        Book book = new Book();
        book.setTitle("Clean Code");
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setCreatedAt(LocalDateTime.of(2025, 6, 4, 9, 0));
        ReservedBookDTO dto = mapper.toDto(reservation);
        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals("John Doe", dto.getUsername());
        assertEquals("Clean Code", dto.getBookTitle());
        assertEquals(LocalDateTime.of(2025, 6, 4, 9, 0), dto.getCreatedAt());
    }
}