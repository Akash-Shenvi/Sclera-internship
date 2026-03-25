package com.sclera.blog.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private boolean published;
    private LocalDateTime createdAt;

    private UserResponse user;
}