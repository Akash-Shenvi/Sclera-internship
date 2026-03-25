package com.sclera.blog.service.impl;

import com.sclera.blog.config.JwtService;
import com.sclera.blog.dto.request.LoginRequest;
import com.sclera.blog.dto.request.RegisterRequest;
import com.sclera.blog.dto.response.AuthResponse;
import com.sclera.blog.entity.Role;
import com.sclera.blog.entity.User;
import com.sclera.blog.exception.BadRequestException;
import com.sclera.blog.exception.ResourceNotFoundException;
import com.sclera.blog.exception.UnauthorizedException;
import com.sclera.blog.repository.UserRepository;
import com.sclera.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .bio(request.getBio())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return new AuthResponse("User registered successfully");
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}
