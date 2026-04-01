package com.sclera.blog.service.impl;

import com.sclera.blog.dto.request.PostRequest;
import com.sclera.blog.dto.response.PostSearchResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public Page<PostResponse> getAllPosts(int page, int size, String sortBy, String sortDir) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Sort sort = buildSort(sortBy, sortDir);

        return postRepository.findByPublishedTrueOrUserId(currentUserId, PageRequest.of(page, size, sort))
                .map(postMapper::toDto);
    }

    @Override
    public Page<PostSearchResponse> searchPostsByTitle(String query, int page, int size, String sortBy, String sortDir) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Sort sort = buildSort(sortBy, sortDir);
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        if (query == null || query.trim().isEmpty()) {
            return postRepository.findByPublishedTrueOrUserId(currentUserId, pageRequest)
                    .map(postMapper::toSearchDto);
        }

        return postRepository.searchVisiblePostsByTitle(query.trim(), currentUserId, pageRequest)
                .map(postMapper::toSearchDto);
    }

    @Override
    public Page<PostResponse> getPostsByUserId(Long userId, int page, int size, String sortBy, String sortDir) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }

        Long currentUserId = SecurityUtils.getCurrentUserId();
        Sort sort = buildSort(sortBy, sortDir);

        if (currentUserId.equals(userId)) {
            return postRepository.findByUserId(userId, PageRequest.of(page, size, sort))
                    .map(postMapper::toDto);
        }

        return postRepository.findByUserIdAndPublishedTrue(userId, PageRequest.of(page, size, sort))
                .map(postMapper::toDto);
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

    private Sort buildSort(String sortBy, String sortDir) {
        return sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
    }
}
