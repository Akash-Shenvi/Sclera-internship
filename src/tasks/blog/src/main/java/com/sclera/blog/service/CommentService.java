package com.sclera.blog.service;

import com.sclera.blog.dto.request.CommentRequest;
import com.sclera.blog.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse addComment(CommentRequest request, Long userId);

    List<CommentResponse> getCommentsByPost(Long postId);
}
