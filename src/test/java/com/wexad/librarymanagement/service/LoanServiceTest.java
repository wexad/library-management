package com.wexad.librarymanagement.service;

import com.wexad.librarymanagement.dto.LoanDTO;
import com.wexad.librarymanagement.entity.Book;
import com.wexad.librarymanagement.entity.Loan;
import com.wexad.librarymanagement.entity.Reservation;
import com.wexad.librarymanagement.entity.User;
import com.wexad.librarymanagement.mapper.LoanMapper;
import com.wexad.librarymanagement.repository.BookRepository;
import com.wexad.librarymanagement.repository.LoanRepository;
import com.wexad.librarymanagement.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private LoanService loanService;

    private Reservation reservation;
    private Book book;
    private User user;

    @BeforeEach
    void setUp() {
        book = Book.builder()
                .title("Test Book")
                .availableCopies(3)
                .build();
        book.setId(1L);
        user = User.builder()
                .id(2L)
                .name("John")
                .email("john@example.com")
                .build();

        reservation = Reservation.builder()
                .book(book)
                .user(user)
                .duration(7)
                .build();
        reservation.setId(1L);
    }

    @Test
    void testSave_ShouldSaveLoan() {
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        loanService.save(1);

        ArgumentCaptor<Loan> loanCaptor = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository).save(loanCaptor.capture());

        Loan savedLoan = loanCaptor.getValue();
        assertEquals(book, savedLoan.getBook());
        assertEquals(user, savedLoan.getUser());
        assertEquals(LocalDate.now(), savedLoan.getIssueDate());
        assertEquals(LocalDate.now().plusDays(reservation.getDuration()), savedLoan.getDueDate());
    }

    @Test
    void testReturnBook_ShouldUpdateLoanAndBook() {
        Loan loan = Loan.builder()
                .book(book)
                .user(user)
                .issueDate(LocalDate.now().minusDays(3))
                .dueDate(LocalDate.now().plusDays(4))
                .build();
        loan.setId(1L);
        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        loanService.returnBook(1);

        assertEquals(LocalDate.now(), loan.getReturnDate());
        assertEquals(4, loan.getBook().getAvailableCopies());

        verify(loanRepository).save(loan);
        verify(bookRepository).save(book);
        verify(reservationRepository).returnBook(user.getId(), book.getId());
    }

    @Test
    void testGetActiveLoans_ShouldReturnLoanDTOList() {
        Loan loan = Loan.builder().build();
        loan.setId(1L);
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(1);

        when(loanRepository.findAllBorrowedBooks()).thenReturn(List.of(loan));
        when(loanMapper.toDtoList(List.of(loan))).thenReturn(List.of(loanDTO));

        List<LoanDTO> result = loanService.getActiveLoans();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testReturnBook_LoanNotFound_ShouldThrowException() {
        when(loanRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                loanService.returnBook(99));

        assertEquals("Loan not found", ex.getMessage());
    }
}