package com.sclera.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank
    private String content;

    private Long postId;

    private Long parentId; // for replies
}