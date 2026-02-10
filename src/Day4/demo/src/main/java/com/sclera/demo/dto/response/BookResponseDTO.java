package com.sclera.demo.dto.response;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDTO {

    private long id;
    private String isbnNumber;
    private String name;
    private String category;
    private Double rating;
    private Double price;

    private Set<AuthorResponseDTO> authors;
}
