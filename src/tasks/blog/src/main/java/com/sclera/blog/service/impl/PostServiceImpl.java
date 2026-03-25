package com.sclera.blog.service.impl;

import com.sclera.blog.dto.request.PostRequest;
import com.sclera.blog.dto.response.PostResponse;
import com.sclera.blog.entity.Post;
import com.sclera.blog.entity.User;
import com.sclera.blog.exception.ResourceNotFoundException;
import com.sclera.blog.exception.UnauthorizedException;
import com.sclera.blog.mapper.PostMapper;
import com.sclera.blog.repository.PostRepository;
import com.sclera.blog.repository.UserRepository;
import com.sclera.blog.security.SecurityUtils;
import com.sclera.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Override
    public PostResponse createPost(PostRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .published(request.isPublished())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        return postMapper.toDto(postRepository.save(post));
    }

    @Override
    public List<PostResponse> getAllPosts() {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        return postRepository.findByPublishedTrueOrUserId(currentUserId)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    @Override
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Long currentUserId = SecurityUtils.getCurrentUserId();

        if (!post.isPublished() && !post.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedException("You are not allowed to view this post");
        }

        return postMapper.toDto(post);
    }

    @Override
    public PostResponse updatePost(Long id, PostRequest request) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Long currentUserId = SecurityUtils.getCurrentUserId();

        if (!post.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedException("You are not allowed to update this post");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());
        post.setPublished(request.isPublished());
        post.setUpdatedAt(LocalDateTime.now());

        return postMapper.toDto(postRepository.save(post));
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Long currentUserId = SecurityUtils.getCurrentUserId();

        if (!post.getUser().getId().equals(currentUserId)) {
            throw new UnauthorizedException("You are not allowed to delete this post");
        }

        postRepository.delete(post);
    }
}
