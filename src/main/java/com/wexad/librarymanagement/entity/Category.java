package com.wexad.librarymanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;
}
