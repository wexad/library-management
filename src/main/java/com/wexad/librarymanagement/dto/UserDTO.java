package com.wexad.librarymanagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String role;
    private String email;
}
