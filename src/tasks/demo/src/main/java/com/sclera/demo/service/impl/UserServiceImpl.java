package com.sclera.demo.service.impl;

import com.sclera.demo.dto.response.UserResponseDTO;
import com.sclera.demo.entity.User;
import com.sclera.demo.exception.ResourceNotFoundException;
import com.sclera.demo.repository.UserRepository;
import com.sclera.demo.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO getCurrentUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return mapToResponse(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsersIfAdmin(Long userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        if (!"ADMIN".equalsIgnoreCase(currentUser.getRole())) {
            throw new AccessDeniedException("You are not admin");
        }

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private UserResponseDTO mapToResponse(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
