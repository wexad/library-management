package com.wexad.librarymanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String role;
}

