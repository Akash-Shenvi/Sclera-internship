package com.sclera.demo.dto.request;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDTO {


    private String isbnNumber;
    private String name;
    private String category;
    private Double rating;
    private Double price;

    private Set<Long> authorIds;
}
