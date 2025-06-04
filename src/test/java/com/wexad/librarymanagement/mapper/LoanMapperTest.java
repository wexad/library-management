package com.wexad.librarymanagement.mapper;

import com.wexad.librarymanagement.dto.LoanDTO;
import com.wexad.librarymanagement.entity.Book;
import com.wexad.librarymanagement.entity.Loan;
import com.wexad.librarymanagement.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LoanMapperTest {

    private final LoanMapper loanMapper = Mappers.getMapper(LoanMapper.class);

    @Test
    void testToDto() {
        User user = new User();
        user.setName("Ali Vali");
        Book book = new Book();
        book.setTitle("Spring Boot 101");
        Loan loan = new Loan();
        loan.setId(42L);
        loan.setUser(user);
        loan.setBook(book);
        loan.setUpdatedAt(LocalDateTime.of(2025, 6, 4, 10, 30));
        loan.setIssueDate(LocalDate.of(2025, 6, 1));
        loan.setReturnDate(null);
        loan.setDueDate(LocalDate.of(2025, 6, 14));
        LoanDTO dto = loanMapper.toDto(loan);
        assertNotNull(dto);
        assertEquals(42, dto.getId());
        assertEquals("Ali Vali", dto.getUsername());
        assertEquals("Spring Boot 101", dto.getTitle());
        assertEquals(LocalDateTime.of(2025, 6, 4, 10, 30), dto.getBorrowedAt());
    }
}