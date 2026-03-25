package com.sclera.blog.service;

import com.sclera.blog.dto.request.PostRequest;
import com.sclera.blog.dto.response.PostResponse;

import java.util.List;

public interface PostService {

    PostResponse createPost(PostRequest request, Long userId);

    List<PostResponse> getAllPosts();

    PostResponse getPostById(Long id);

    PostResponse updatePost(Long id, PostRequest request);

    void deletePost(Long id);
}
