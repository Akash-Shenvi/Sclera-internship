package com.sclera.blog.service;

import com.sclera.blog.dto.request.CommentRequest;
import com.sclera.blog.dto.response.CommentResponse;
import org.springframework.data.domain.Page;

public interface CommentService {

    CommentResponse addComment(CommentRequest request, Long userId);
    void deleteComment(Long commentId, Long userId);
    void deleteAllCommentsByPost(Long postId, Long userId);

    Page<CommentResponse> getCommentsByPost(Long postId, Long userId, int page, int size, String sortBy, String sortDir);
}
