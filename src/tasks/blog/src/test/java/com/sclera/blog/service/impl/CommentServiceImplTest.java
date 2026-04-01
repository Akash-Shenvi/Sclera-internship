package com.sclera.blog.service.impl;

import com.sclera.blog.dto.request.CommentRequest;
import com.sclera.blog.dto.response.CommentResponse;
import com.sclera.blog.entity.Comment;
import com.sclera.blog.entity.Post;
import com.sclera.blog.entity.User;
import com.sclera.blog.exception.BadRequestException;
import com.sclera.blog.mapper.CommentMapper;
import com.sclera.blog.repository.CommentRepository;
import com.sclera.blog.repository.PostRepository;
import com.sclera.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void addCommentShouldAllowReplyAtThirdLevel() {
        Long userId = 1L;
        Post post = Post.builder().id(10L).published(true).user(User.builder().id(2L).build()).build();
        User user = User.builder().id(userId).build();
        Comment root = Comment.builder().id(100L).post(post).build();
        Comment secondLevel = Comment.builder().id(101L).post(post).parent(root).build();

        CommentRequest request = new CommentRequest();
        request.setContent("Third level reply");
        request.setPostId(10L);
        request.setParentId(101L);

        Comment savedComment = Comment.builder().id(102L).content("Third level reply").post(post).parent(secondLevel).user(user).build();
        CommentResponse response = new CommentResponse();
        response.setId(102L);
        response.setContent("Third level reply");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(10L)).thenReturn(Optional.of(post));
        when(commentRepository.findById(101L)).thenReturn(Optional.of(secondLevel));
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);
        when(commentMapper.toDto(savedComment)).thenReturn(response);

        CommentResponse result = commentService.addComment(request, userId);

        ArgumentCaptor<Comment> savedCommentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(savedCommentCaptor.capture());

        Comment toSave = savedCommentCaptor.getValue();
        assertEquals(102L, result.getId());
        assertEquals("Third level reply", result.getContent());
        assertEquals(secondLevel, toSave.getParent());
        assertEquals(post, toSave.getPost());
        assertEquals(user, toSave.getUser());
        assertNull(root.getParent());
    }

    @Test
    void addCommentShouldRejectReplyBeyondThirdLevel() {
        Long userId = 1L;
        Post post = Post.builder().id(10L).published(true).user(User.builder().id(2L).build()).build();
        User user = User.builder().id(userId).build();
        Comment root = Comment.builder().id(100L).post(post).build();
        Comment secondLevel = Comment.builder().id(101L).post(post).parent(root).build();
        Comment thirdLevel = Comment.builder().id(102L).post(post).parent(secondLevel).build();

        CommentRequest request = new CommentRequest();
        request.setContent("Fourth level reply");
        request.setPostId(10L);
        request.setParentId(102L);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(10L)).thenReturn(Optional.of(post));
        when(commentRepository.findById(102L)).thenReturn(Optional.of(thirdLevel));

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> commentService.addComment(request, userId)
        );

        assertEquals("Maximum comment depth is 3 levels", exception.getMessage());
        verify(commentRepository, never()).save(any(Comment.class));
    }
}
