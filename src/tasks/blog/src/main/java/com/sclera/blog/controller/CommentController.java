package com.sclera.blog.controller;

import com.sclera.blog.dto.request.CommentRequest;
import com.sclera.blog.dto.response.CommentResponse;
import com.sclera.blog.security.SecurityUtils;
import com.sclera.blog.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Add comment or reply
    @PostMapping
    public CommentResponse addComment(@Valid @RequestBody CommentRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        return commentService.addComment(request, userId);
    }

    // Get all comments of a post
    @GetMapping("/post/{postId}")
    public Page<CommentResponse> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ) {
        Long userId = SecurityUtils.getCurrentUserId();
        return commentService.getCommentsByPost(postId, userId, page, size, sortBy, sortDir);
    }

    // Delete own comment/reply
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        Long userId = SecurityUtils.getCurrentUserId();
        commentService.deleteComment(commentId, userId);
    }

    // Delete all comments of a post (post owner only)
    @DeleteMapping("/post/{postId}")
    public void deleteAllCommentsByPost(@PathVariable Long postId) {
        Long userId = SecurityUtils.getCurrentUserId();
        commentService.deleteAllCommentsByPost(postId, userId);
    }
}
