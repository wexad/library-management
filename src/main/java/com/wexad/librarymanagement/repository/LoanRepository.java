package com.wexad.librarymanagement.repository;

import com.wexad.librarymanagement.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query(value = "SELECT * FROM loans WHERE return_date is null", nativeQuery = true)
    List<Loan> findAllBorrowedBooks();
}
