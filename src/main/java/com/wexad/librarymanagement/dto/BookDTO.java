package com.wexad.librarymanagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BookDTO {
    private int id;
    private String title;
    private String author;
    private String description;
    private int totalCopies;
    private CategoryDTO category;
    private ImageDTO image;
}
