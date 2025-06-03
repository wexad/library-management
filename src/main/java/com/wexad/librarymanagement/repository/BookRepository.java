package com.wexad.librarymanagement.repository;

import com.wexad.librarymanagement.entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = "SELECT * FROM books WHERE active = false", nativeQuery = true)
    List<Book> findAllDeleted();

    @Modifying
    @Transactional
    @Query(value = "UPDATE books SET active = false WHERE id = :id", nativeQuery = true)
    void softDeleteById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE books SET active = true WHERE id = :id", nativeQuery = true)
    void restoreById(@Param("id") Long id);

    @Query(value = "SELECT * FROM books b " +
            "WHERE (:keyword IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:categoryId IS NULL OR b.category_id = :categoryId)", nativeQuery = true)
    List<Book> searchByKeywordAndCategory(@Param("keyword") String keyword,
                                          @Param("categoryId") Integer categoryId);

    @Query(value = """
                SELECT 
                    CASE 
                        WHEN b.total_copies - (
                            (
                                SELECT COUNT(*) FROM loans l
                                WHERE l.book_id = :book_id
                                  AND l.issue_date <= :return_date
                                  AND l.return_date >= :pickup_date
                            )
                            +
                            (
                                SELECT COUNT(*) FROM reservations r
                                WHERE r.book_id = :book_id
                                  AND r.status = 'reserved'
                                  AND DATE(r.created_at) BETWEEN :pickup_date AND :return_date
                            )
                        ) > 0
                    THEN TRUE
                    ELSE FALSE
                    END AS is_available
                FROM books b
                WHERE b.id = :book_id
                LIMIT 1
            """, nativeQuery = true)
    Boolean isAvailable(@Param("book_id") Integer bookId,
                        @Param("pickup_date") LocalDate pickupDate,
                        @Param("return_date") LocalDate returnDate);


}
