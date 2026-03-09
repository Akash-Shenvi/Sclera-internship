package com.sclera.demo.controller;

import com.sclera.demo.dto.request.LoginRequestDTO;
import com.sclera.demo.dto.request.RegisterRequestDTO;
import com.sclera.demo.dto.response.AuthResponseDTO;
import com.sclera.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register (@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        AuthResponseDTO created = authService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login (@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        AuthResponseDTO created = authService.login(loginRequestDTO);
        return ResponseEntity.ok(authService.login(loginRequestDTO));    }

}
