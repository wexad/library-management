package com.wexad.librarymanagement.service;

import com.wexad.librarymanagement.dto.ReservationDTO;
import com.wexad.librarymanagement.entity.Book;
import com.wexad.librarymanagement.entity.Reservation;
import com.wexad.librarymanagement.mapper.ReservationMapper;
import com.wexad.librarymanagement.repository.BookRepository;
import com.wexad.librarymanagement.repository.ReservationRepository;
import com.wexad.librarymanagement.repository.UserRepository;
import com.wexad.librarymanagement.util.SessionUser;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BookService bookService;
    private final SessionUser sessionUser;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(ReservationRepository reservationRepository, BookService bookService, SessionUser sessionUser, BookRepository bookRepository, UserRepository userRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.bookService = bookService;
        this.sessionUser = sessionUser;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.reservationMapper = reservationMapper;
    }

    public void reserve(Integer bookId, LocalDate pickupDate, LocalDate returnDate) {
        bookService.isAvailable(bookId, pickupDate, returnDate);
        int userId = sessionUser.getUserId();
        Book book = bookRepository.findById(Long.valueOf(bookId)).orElseThrow(() -> new RuntimeException("Book not found"));
        Reservation reserved = Reservation.builder()
                .book(book)
                .status("reserved")
                .user(userRepository.getById((long) userId))
                .build();
        reservationRepository.save(reserved);
    }

    public List<ReservationDTO> getUserReservations() {
        int userId = sessionUser.getUserId();
        return reservationMapper.toDtoList(reservationRepository.findUserReservations(userId));
    }

}
