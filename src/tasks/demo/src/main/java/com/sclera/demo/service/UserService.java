package com.sclera.demo.service;

import com.sclera.demo.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO getCurrentUser(Long userId);
    List<UserResponseDTO> getAllUsersIfAdmin(Long userId);
}
