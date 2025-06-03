package com.wexad.librarymanagement.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReservedBookDTO {
    private int id;
    private String username;
    private String bookTitle;
    private LocalDateTime createdAt;
}
