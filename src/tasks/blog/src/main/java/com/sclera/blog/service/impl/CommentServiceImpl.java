package com.sclera.blog.service.impl;

import com.sclera.blog.dto.request.CommentRequest;
import com.sclera.blog.dto.response.CommentResponse;
import com.sclera.blog.entity.Comment;
import com.sclera.blog.entity.Post;
import com.sclera.blog.entity.User;
import com.sclera.blog.exception.ResourceNotFoundException;
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

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        Comment parent = null;

        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent comment not found"));
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
    public List<CommentResponse> getCommentsByPost(Long postId) {
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
}
