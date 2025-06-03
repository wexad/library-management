package com.wexad.librarymanagement.repository;

import com.wexad.librarymanagement.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query(value = """
                SELECT * FROM reservations
                WHERE user_id = :userId
                  AND status IN ('reserved', 'borrowed', 'returned', 'blocked')
                ORDER BY id DESC
            """, nativeQuery = true)
    List<Reservation> findUserReservations(@Param("userId") Integer userId);

}
