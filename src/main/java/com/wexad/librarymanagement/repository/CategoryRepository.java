package com.wexad.librarymanagement.repository;

import com.wexad.librarymanagement.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT * FROM categories WHERE active = false", nativeQuery = true)
    List<Category> findAllDeleted();

    @Modifying
    @Transactional
    @Query(value = "UPDATE categories SET active = false WHERE id = :id", nativeQuery = true)
    void softDeleteById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE categories SET active = true WHERE id = :id", nativeQuery = true)
    void restoreById(@Param("id") Long id);
}
