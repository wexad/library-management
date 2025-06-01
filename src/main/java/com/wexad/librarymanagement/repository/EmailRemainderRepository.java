package com.wexad.librarymanagement.repository;

import com.wexad.librarymanagement.entity.EmailReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRemainderRepository extends JpaRepository<EmailReminder, Long> {
}
