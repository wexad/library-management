package com.wexad.librarymanagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReservationDTO {
    private String name;
    private String status;
}
