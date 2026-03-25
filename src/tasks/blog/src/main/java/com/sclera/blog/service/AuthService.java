package com.sclera.blog.service;

import com.sclera.blog.dto.request.LoginRequest;
import com.sclera.blog.dto.request.RegisterRequest;
import com.sclera.blog.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}