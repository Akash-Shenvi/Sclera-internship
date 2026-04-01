package com.sclera.blog.service;

import com.sclera.blog.dto.request.PostRequest;
import com.sclera.blog.dto.response.PostSearchResponse;
import com.sclera.blog.dto.response.PostResponse;
import org.springframework.data.domain.Page;

public interface PostService {

    PostResponse createPost(PostRequest request, Long userId);

    Page<PostResponse> getAllPosts(int page, int size, String sortBy, String sortDir);
    Page<PostSearchResponse> searchPostsByTitle(String query, int page, int size, String sortBy, String sortDir);
    Page<PostResponse> getPostsByUserId(Long userId, int page, int size, String sortBy, String sortDir);

    PostResponse getPostById(Long id);

    PostResponse updatePost(Long id, PostRequest request);

    void deletePost(Long id);
}
