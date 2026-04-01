package com.sclera.blog.service.impl;

import com.sclera.blog.dto.request.CommentRequest;
import com.sclera.blog.dto.response.CommentResponse;
import com.sclera.blog.entity.Comment;
import com.sclera.blog.entity.Post;
import com.sclera.blog.entity.User;
import com.sclera.blog.exception.BadRequestException;
import com.sclera.blog.exception.ResourceNotFoundException;
import com.sclera.blog.exception.UnauthorizedException;
import com.sclera.blog.mapper.CommentMapper;
import com.sclera.blog.repository.CommentRepository;
import com.sclera.blog.repository.PostRepository;
import com.sclera.blog.repository.UserRepository;
import com.sclera.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private static final int MAX_COMMENT_DEPTH = 3;

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponse addComment(CommentRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (request.getPostId() == null) {
            throw new BadRequestException("postId is required");
        }

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        validateCanInteractWithPost(post, userId);

        Comment parent = null;

        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent comment not found"));
            if (!parent.getPost().getId().equals(post.getId())) {
                throw new BadRequestException("Parent comment does not belong to the provided post");
            }
            validateReplyDepth(parent);
        }

        Comment comment = Comment.builder()
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .user(user)
                .post(post)
                .parent(parent)
                .build();

        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You are not allowed to delete this comment");
        }

        commentRepository.delete(comment);
    }

    @Override
    public void deleteAllCommentsByPost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        if (!post.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You are not allowed to delete comments for this post");
        }

        List<Comment> rootComments = commentRepository.findByPostIdAndParentIsNull(postId);
        commentRepository.deleteAll(rootComments);
    }

    @Override
    public Page<CommentResponse> getCommentsByPost(Long postId, Long userId, int page, int size, String sortBy, String sortDir) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        validateCanViewPost(post, userId);
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        return commentRepository.findByPostIdAndParentIsNull(postId, PageRequest.of(page, size, sort))
                .map(this::mapToResponseWithReplies);
    }

    private CommentResponse mapToResponseWithReplies(Comment comment) {
        CommentResponse response = commentMapper.toDto(comment);

        List<Comment> replies = commentRepository.findByParentId(comment.getId());

        response.setReplies(
                replies.stream()
                        .map(this::mapToResponseWithReplies)
                        .toList()
        );

        return response;
    }

    private void validateCanInteractWithPost(Post post, Long userId) {
        if (!post.isPublished() && !post.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You are not allowed to comment on this post");
        }
    }

    private void validateCanViewPost(Post post, Long userId) {
        if (!post.isPublished() && !post.getUser().getId().equals(userId)) {
            throw new UnauthorizedException("You are not allowed to view comments for this post");
        }
    }

    private void validateReplyDepth(Comment parent) {
        int newCommentDepth = getCommentDepth(parent) + 1;
        if (newCommentDepth > MAX_COMMENT_DEPTH) {
            throw new BadRequestException("Maximum comment depth is 3 levels");
        }
    }

    private int getCommentDepth(Comment comment) {
        int depth = 1;
        Comment current = comment;

        while (current.getParent() != null) {
            depth++;
            current = current.getParent();
        }

        return depth;
    }
}
