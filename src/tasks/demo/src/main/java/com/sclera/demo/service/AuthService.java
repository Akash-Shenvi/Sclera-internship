package com.sclera.demo.service;

import com.sclera.demo.dto.request.AuthorRequestDTO;
import com.sclera.demo.dto.request.LoginRequestDTO;
import com.sclera.demo.dto.request.RegisterRequestDTO;
import com.sclera.demo.dto.response.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO registerRequestDTO);
    AuthResponseDTO login (LoginRequestDTO loginRequestDTO);
}
