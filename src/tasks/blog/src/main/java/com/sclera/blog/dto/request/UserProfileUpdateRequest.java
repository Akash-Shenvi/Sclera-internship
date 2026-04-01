package com.sclera.blog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserProfileUpdateRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Size(max = 50)
    private String username;

    @Size(max = 500)
    private String bio;
}
