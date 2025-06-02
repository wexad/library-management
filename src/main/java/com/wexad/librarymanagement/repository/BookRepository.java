package com.wexad.librarymanagement.repository;

import com.wexad.librarymanagement.entity.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
