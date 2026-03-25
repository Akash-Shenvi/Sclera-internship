package com.sclera.blog.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentResponse {

    private Long id;
    private String content;
    private LocalDateTime createdAt;

    private UserResponse user;

    private List<CommentResponse> replies; // nested
}