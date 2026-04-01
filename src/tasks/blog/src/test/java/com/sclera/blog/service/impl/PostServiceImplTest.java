package com.sclera.blog.service.impl;

import com.sclera.blog.dto.response.PostSearchResponse;
import com.sclera.blog.entity.Post;
import com.sclera.blog.entity.User;
import com.sclera.blog.mapper.PostMapper;
import com.sclera.blog.repository.PostRepository;
import com.sclera.blog.repository.UserRepository;
import com.sclera.blog.security.CustomUserDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceImpl postService;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void searchPostsByTitleShouldReturnSummaryResultsForMatchingTitle() {
        authenticateAs(1L);

        Post post = Post.builder()
                .id(10L)
                .title("Spring Security Guide")
                .build();

        PostSearchResponse response = new PostSearchResponse();
        response.setId(10L);
        response.setTitle("Spring Security Guide");

        Page<Post> page = new PageImpl<>(List.of(post));

        when(postRepository.searchVisiblePostsByTitle(eq("spring"), eq(1L), any(PageRequest.class))).thenReturn(page);
        when(postMapper.toSearchDto(post)).thenReturn(response);

        Page<PostSearchResponse> result = postService.searchPostsByTitle(" spring ", 0, 10, "createdAt", "desc");

        assertEquals(1, result.getTotalElements());
        assertEquals("Spring Security Guide", result.getContent().getFirst().getTitle());
        verify(postRepository).searchVisiblePostsByTitle(eq("spring"), eq(1L), any(PageRequest.class));
        verify(postRepository, never()).findByPublishedTrueOrUserId(eq(1L), any(PageRequest.class));
    }

    @Test
    void searchPostsByTitleShouldReturnVisiblePostsWhenQueryIsBlank() {
        authenticateAs(1L);

        Post post = Post.builder()
                .id(11L)
                .title("Visible Draft")
                .build();

        PostSearchResponse response = new PostSearchResponse();
        response.setId(11L);
        response.setTitle("Visible Draft");

        Page<Post> page = new PageImpl<>(List.of(post));

        when(postRepository.findByPublishedTrueOrUserId(eq(1L), any(PageRequest.class))).thenReturn(page);
        when(postMapper.toSearchDto(post)).thenReturn(response);

        Page<PostSearchResponse> result = postService.searchPostsByTitle("   ", 0, 10, "createdAt", "desc");

        assertEquals(1, result.getTotalElements());
        assertEquals("Visible Draft", result.getContent().getFirst().getTitle());
        verify(postRepository).findByPublishedTrueOrUserId(eq(1L), any(PageRequest.class));
        verify(postRepository, never()).searchVisiblePostsByTitle(eq(""), eq(1L), any(PageRequest.class));
    }

    private void authenticateAs(Long userId) {
        User user = User.builder()
                .id(userId)
                .email("user@example.com")
                .password("encoded-password")
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
