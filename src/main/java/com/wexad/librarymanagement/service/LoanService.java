package com.wexad.librarymanagement.service;

import com.wexad.librarymanagement.dto.LoanDTO;
import com.wexad.librarymanagement.entity.Loan;
import com.wexad.librarymanagement.entity.Reservation;
import com.wexad.librarymanagement.mapper.LoanMapper;
import com.wexad.librarymanagement.repository.BookRepository;
import com.wexad.librarymanagement.repository.LoanRepository;
import com.wexad.librarymanagement.repository.ReservationRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;
    private final LoanMapper loanMapper;
    private final BookRepository bookRepository;

    public LoanService(@Lazy LoanRepository loanRepository, ReservationService reservationService, ReservationRepository reservationRepository, LoanMapper loanMapper, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.reservationRepository = reservationRepository;
        this.loanMapper = loanMapper;
        this.bookRepository = bookRepository;
    }

    public void save(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(Long.valueOf(reservationId))
                .orElseThrow();

        loanRepository.save(
                Loan.builder()
                        .user(reservation.getUser())
                        .book(reservation.getBook())
                        .issueDate(LocalDate.now())
                        .dueDate(LocalDate.now().plusDays(reservation.getDuration()))
                        .build()
        );
    }
    public List<LoanDTO> getActiveLoans() {
        return loanMapper.toDtoList(loanRepository.findAllBorrowedBooks());
    }

    public void returnBook(int loanId) {
        Loan loan = loanRepository.findById((long) loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
        loan.getBook().setAvailableCopies(loan.getBook().getAvailableCopies() + 1);
        bookRepository.save(loan.getBook());
        reservationRepository.returnBook(loan.getUser().getId(), loan.getBook().getId());
    }
}
