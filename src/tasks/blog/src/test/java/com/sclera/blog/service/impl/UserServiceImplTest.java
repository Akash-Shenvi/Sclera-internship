package com.sclera.blog.service.impl;

import com.sclera.blog.dto.request.UserProfileUpdateRequest;
import com.sclera.blog.dto.response.UserResponse;
import com.sclera.blog.dto.response.UserSearchResponse;
import com.sclera.blog.entity.User;
import com.sclera.blog.exception.BadRequestException;
import com.sclera.blog.mapper.UserMapper;
import com.sclera.blog.repository.UserRepository;
import com.sclera.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PostService postService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getCurrentUserProfileShouldReturnMappedUser() {
        User user = User.builder()
                .id(1L)
                .name("Akash")
                .username("akash.dev")
                .email("akash@example.com")
                .bio("Java developer")
                .build();

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setName("Akash");
        response.setUsername("akash.dev");
        response.setEmail("akash@example.com");
        response.setBio("Java developer");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(response);

        UserResponse result = userService.getCurrentUserProfile(1L);

        assertEquals("Akash", result.getName());
        assertEquals("akash.dev", result.getUsername());
        assertEquals("akash@example.com", result.getEmail());
    }

    @Test
    void updateCurrentUserProfileShouldSaveUpdatedFields() {
        User user = User.builder()
                .id(1L)
                .name("Akash")
                .username("akash.dev")
                .email("akash@example.com")
                .bio("Old bio")
                .build();

        UserProfileUpdateRequest request = new UserProfileUpdateRequest();
        request.setName("Akash Sharma");
        request.setUsername("akash.sharma");
        request.setBio("Updated bio");

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setName("Akash Sharma");
        response.setUsername("akash.sharma");
        response.setEmail("akash@example.com");
        response.setBio("Updated bio");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsernameIgnoreCase("akash.sharma")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userMapper.toDto(any(User.class))).thenReturn(response);

        UserResponse result = userService.updateCurrentUserProfile(1L, request);

        ArgumentCaptor<User> savedUserCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(savedUserCaptor.capture());

        User savedUser = savedUserCaptor.getValue();
        assertEquals("Akash Sharma", savedUser.getName());
        assertEquals("akash.sharma", savedUser.getUsername());
        assertEquals("Updated bio", savedUser.getBio());
        assertEquals("akash.sharma", result.getUsername());
    }

    @Test
    void updateCurrentUserProfileShouldThrowWhenUsernameAlreadyExists() {
        User user = User.builder()
                .id(1L)
                .name("Akash")
                .username("akash.dev")
                .email("akash@example.com")
                .build();

        UserProfileUpdateRequest request = new UserProfileUpdateRequest();
        request.setName("Akash");
        request.setUsername("another.user");
        request.setBio("Bio");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsernameIgnoreCase("another.user")).thenReturn(true);

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> userService.updateCurrentUserProfile(1L, request)
        );

        assertEquals("Username already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void searchUsersShouldReturnAllUsersWhenQueryIsBlank() {
        User user = User.builder()
                .id(1L)
                .name("Akash")
                .username("akash.dev")
                .email("akash@example.com")
                .build();

        UserSearchResponse response = new UserSearchResponse();
        response.setId(1L);
        response.setName("Akash");
        response.setUsername("akash.dev");
        response.setEmail("akash@example.com");

        Page<User> page = new PageImpl<>(List.of(user));

        when(userRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(userMapper.toSearchDto(user)).thenReturn(response);

        Page<UserSearchResponse> result = userService.searchUsers("   ", 0, 10, "name", "asc");

        assertEquals(1, result.getTotalElements());
        assertEquals("akash.dev", result.getContent().getFirst().getUsername());
        verify(userRepository).findAll(any(PageRequest.class));
    }

    @Test
    void searchUsersShouldMatchUsernameOrNameWithSingleQuery() {
        User user = User.builder()
                .id(2L)
                .name("Akash Sharma")
                .username("akash.sharma")
                .email("akash.sharma@example.com")
                .build();

        UserSearchResponse response = new UserSearchResponse();
        response.setId(2L);
        response.setName("Akash Sharma");
        response.setUsername("akash.sharma");
        response.setEmail("akash.sharma@example.com");

        Page<User> page = new PageImpl<>(List.of(user));

        when(userRepository.findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                eq("akash"),
                eq("akash"),
                eq("akash"),
                any(PageRequest.class)
        )).thenReturn(page);
        when(userMapper.toSearchDto(user)).thenReturn(response);

        Page<UserSearchResponse> result = userService.searchUsers(" akash ", 0, 10, "name", "asc");

        assertEquals(1, result.getTotalElements());
        assertEquals("Akash Sharma", result.getContent().getFirst().getName());
        assertEquals("akash.sharma", result.getContent().getFirst().getUsername());
        verify(userRepository).findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                eq("akash"),
                eq("akash"),
                eq("akash"),
                any(PageRequest.class)
        );
    }
}
