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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void registerShouldSaveUserWithEncodedPassword() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Akash");
        request.setUsername("akash.dev");
        request.setEmail("akash@example.com");
        request.setPassword("plain-password");
        request.setBio("Java developer");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByUsernameIgnoreCase(request.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-password");

        AuthResponse response = authService.register(request);

        ArgumentCaptor<User> savedUserCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(savedUserCaptor.capture());

        User savedUser = savedUserCaptor.getValue();
        assertEquals("Akash", savedUser.getName());
        assertEquals("akash.dev", savedUser.getUsername());
        assertEquals("akash@example.com", savedUser.getEmail());
        assertEquals("encoded-password", savedUser.getPassword());
        assertEquals("Java developer", savedUser.getBio());
        assertEquals(Role.USER, savedUser.getRole());
        assertEquals("User registered successfully", response.getToken());
    }

    @Test
    void registerShouldThrowWhenEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("akash.dev");
        request.setEmail("akash@example.com");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> authService.register(request));

        assertEquals("Email already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void registerShouldThrowWhenUsernameAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("akash.dev");
        request.setEmail("akash@example.com");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByUsernameIgnoreCase(request.getUsername())).thenReturn(true);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> authService.register(request));

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void loginShouldReturnTokenWhenCredentialsAreValid() {
        LoginRequest request = new LoginRequest();
        request.setEmail("akash@example.com");
        request.setPassword("plain-password");

        User user = User.builder()
                .id(1L)
                .email("akash@example.com")
                .password("encoded-password")
                .build();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user.getEmail())).thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        assertEquals("jwt-token", response.getToken());
        verify(jwtService).generateToken("akash@example.com");
    }

    @Test
    void loginShouldThrowWhenUserIsNotFound() {
        LoginRequest request = new LoginRequest();
        request.setEmail("missing@example.com");
        request.setPassword("plain-password");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> authService.login(request)
        );

        assertEquals("User not found", exception.getMessage());
        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void loginShouldThrowWhenPasswordIsInvalid() {
        LoginRequest request = new LoginRequest();
        request.setEmail("akash@example.com");
        request.setPassword("wrong-password");

        User user = User.builder()
                .id(1L)
                .email("akash@example.com")
                .password("encoded-password")
                .build();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(false);

        UnauthorizedException exception = assertThrows(
                UnauthorizedException.class,
                () -> authService.login(request)
        );

        assertEquals("Invalid password", exception.getMessage());
        verify(jwtService, never()).generateToken(any());
    }
}
