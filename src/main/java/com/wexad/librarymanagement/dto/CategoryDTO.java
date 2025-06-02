package com.wexad.librarymanagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CategoryDTO {
    private int id;
    private String name;
    private ImageDTO image;
}
