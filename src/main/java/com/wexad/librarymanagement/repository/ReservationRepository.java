package com.wexad.librarymanagement.repository;

import com.wexad.librarymanagement.entity.Loan;
import com.wexad.librarymanagement.entity.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = """
                SELECT * FROM reservations
                WHERE user_id = :userId
                  AND status IN ('reserved', 'borrowed', 'returned', 'canceled')
                ORDER BY id DESC
            """, nativeQuery = true)
    List<Reservation> findUserReservations(@Param("userId") Long userId);

    @Query(value = "FROM Reservation WHERE status = 'reserved'")
    List<Reservation> findAllReservedBooks();

    @Transactional
    @Modifying
    @Query(value = """ 
            UPDATE reservations
            SET status = 'returned'
            WHERE user_id = :userId and book_id = :bookId and status = 'borrowed'
            """, nativeQuery = true)
    void returnBook(@Param("userId") Long userId, @Param("bookId") Long bookId);
}
