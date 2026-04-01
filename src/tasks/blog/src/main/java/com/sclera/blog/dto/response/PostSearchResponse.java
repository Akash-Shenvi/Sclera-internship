package com.sclera.blog.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostSearchResponse {

    private Long id;
    private String title;
    private String excerpt;
    private String imageUrl;
    private boolean published;
    private LocalDateTime createdAt;
    private UserSummaryResponse user;
}
