package com.wexad.librarymanagement.service;

import com.wexad.librarymanagement.dto.ReservationDTO;
import com.wexad.librarymanagement.dto.ReservedBookDTO;
import com.wexad.librarymanagement.entity.Book;
import com.wexad.librarymanagement.entity.Reservation;
import com.wexad.librarymanagement.mapper.ReservationMapper;
import com.wexad.librarymanagement.mapper.ReservedBookMapper;
import com.wexad.librarymanagement.repository.BookRepository;
import com.wexad.librarymanagement.repository.ReservationRepository;
import com.wexad.librarymanagement.repository.UserRepository;
import com.wexad.librarymanagement.util.SessionUser;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BookService bookService;
    private final SessionUser sessionUser;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;
    private final ReservedBookMapper reservedBookMapper;
    private final LoanService loanService;

    public ReservationService(ReservationRepository reservationRepository, BookService bookService, SessionUser sessionUser, BookRepository bookRepository, UserRepository userRepository, ReservationMapper reservationMapper, ReservedBookMapper reservedBookMapper, @Lazy LoanService loanService) {
        this.reservationRepository = reservationRepository;
        this.bookService = bookService;
        this.sessionUser = sessionUser;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.reservationMapper = reservationMapper;
        this.reservedBookMapper = reservedBookMapper;
        this.loanService = loanService;
    }

    public void reserve(Integer bookId, LocalDate pickupDate, LocalDate returnDate) {
        bookService.isAvailable(bookId, pickupDate, returnDate);
        long userId = sessionUser.getUserId();
        Book book = bookRepository.getReferenceById(Long.valueOf(bookId));
        Reservation reserved = Reservation.builder()
                .book(book)
                .status("reserved")
                .user(userRepository.getById(userId))
                .build();
        reservationRepository.save(reserved);
    }

    public List<ReservationDTO> getUserReservations() {
        long userId = sessionUser.getUserId();
        return reservationMapper.toDtoList(reservationRepository.findUserReservations(userId));
    }

    public List<ReservedBookDTO> getReservedBooks() {
        return reservedBookMapper.toDtoList(reservationRepository.findAllReservedBooks());
    }

    @Transactional
    public void issue(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(Long.valueOf(reservationId))
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (reservation.getBook().getAvailableCopies() <= 0) {
            throw new IllegalStateException("No available copies to issue");
        }

        reservation.setStatus("borrowed"); // reserved
        reservation.getBook().setAvailableCopies(reservation.getBook().getAvailableCopies() - 1);

        bookRepository.save(reservation.getBook());
        reservationRepository.save(reservation);

        loanService.save(reservationId);
    }

    public void cancel(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(Long.valueOf(reservationId))
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        reservation.setStatus("canceled");
        reservationRepository.save(reservation);
    }
}
