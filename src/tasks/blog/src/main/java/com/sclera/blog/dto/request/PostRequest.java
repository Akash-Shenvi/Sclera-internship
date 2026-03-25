package com.sclera.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostRequest {

    @NotBlank
    private String title;

    private String content;

    private String imageUrl;

    private boolean published;
}