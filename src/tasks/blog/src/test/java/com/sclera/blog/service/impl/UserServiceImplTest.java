package com.sclera.blog.service.impl;

import com.sclera.blog.dto.response.UserSearchResponse;
import com.sclera.blog.entity.User;
import com.sclera.blog.mapper.UserMapper;
import com.sclera.blog.repository.UserRepository;
import com.sclera.blog.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
