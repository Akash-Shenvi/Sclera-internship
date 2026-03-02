package com.sclera.demo.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String country;
}
