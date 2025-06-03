package com.wexad.librarymanagement.repository;

import com.wexad.librarymanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * from users where id = :userId", nativeQuery = true)
    User getUsersById(@Param("userId") int userId);
}
