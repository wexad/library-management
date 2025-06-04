package com.wexad.librarymanagement.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReservationDTO {
    private String name;
    private String status;
    private LocalDateTime dueDate;
}
