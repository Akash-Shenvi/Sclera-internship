package com.sclera.demo.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 8,message = "password must be atleast 8 char")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
            message = "Password must have at least 8 chars, 1 uppercase, 1 lowercase, 1 number, 1 special character"    )
    private String password;
}
