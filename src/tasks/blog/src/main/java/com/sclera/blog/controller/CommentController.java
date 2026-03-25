package com.sclera.blog.controller;

import com.sclera.blog.dto.request.CommentRequest;
import com.sclera.blog.dto.request.ReplyRequest;
import com.sclera.blog.dto.response.CommentResponse;
import com.sclera.blog.security.SecurityUtils;
import com.sclera.blog.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Reply to an existing comment
    @PostMapping("/{commentId}/replies")
    public CommentResponse addReply(
            @PathVariable Long commentId,
            @Valid @RequestBody ReplyRequest request
    ) {
        Long userId = SecurityUtils.getCurrentUserId();
        return commentService.replyToComment(commentId, request.getContent(), userId);
    }

    // Get all comments of a post
    @GetMapping("/post/{postId}")
    public List<CommentResponse> getComments(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }

    // Delete own comment/reply
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        Long userId = SecurityUtils.getCurrentUserId();
        commentService.deleteComment(commentId, userId);
    }
}
