package com.sclera.demo.service.impl;

import com.sclera.demo.dto.request.LoginRequestDTO;
import com.sclera.demo.dto.request.RegisterRequestDTO;
import com.sclera.demo.dto.response.AuthResponseDTO;
import com.sclera.demo.entity.User;
import com.sclera.demo.repository.UserRepository;
import com.sclera.demo.security.jwt.JwtService;
import com.sclera.demo.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return AuthResponseDTO.builder()
                .message("Login successful")
                .token(token)
                .build();
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        if(userRepository.existsByEmail(registerRequestDTO.getEmail())){
            throw new RuntimeException("Email already exists ");
        }
        User user= User.builder()
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .role("USER")
                .build();
        userRepository.save(user);
        return AuthResponseDTO.builder().token(null).message("Registration success").build();
    }

}
