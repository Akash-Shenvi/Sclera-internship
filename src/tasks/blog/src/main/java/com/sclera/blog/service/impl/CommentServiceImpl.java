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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

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
    public CommentResponse replyToComment(Long parentCommentId, String content, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Comment parent = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new ResourceNotFoundException("Parent comment not found"));
        validateCanInteractWithPost(parent.getPost(), userId);

        Comment reply = Comment.builder()
                .content(content)
                .createdAt(LocalDateTime.now())
                .user(user)
                .post(parent.getPost())
                .parent(parent)
                .build();

        return commentMapper.toDto(commentRepository.save(reply));
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
    public List<CommentResponse> getCommentsByPost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        validateCanViewPost(post, userId);

        List<Comment> rootComments = commentRepository.findByPostIdAndParentIsNull(postId);

        return rootComments.stream()
                .map(this::mapToResponseWithReplies)
                .toList();
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
}
