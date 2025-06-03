package com.wexad.librarymanagement.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoanDTO {
    private int id;
    private String title;
    private String username;
    private LocalDateTime borrowedAt;
}
