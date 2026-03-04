package com.sclera.demo.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorAvgRatingResponseDTO {
    private Long authorId;
    private String fullName;
    private String country;
    private Double avgRating;
}
