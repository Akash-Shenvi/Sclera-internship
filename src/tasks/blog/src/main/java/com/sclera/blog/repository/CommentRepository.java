package com.sclera.blog.repository;

import com.sclera.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Get all comments of a post
    List<Comment> findByPostId(Long postId);

    // Get top-level comments (no parent)
    List<Comment> findByPostIdAndParentIsNull(Long postId);
    Page<Comment> findByPostIdAndParentIsNull(Long postId, Pageable pageable);

    // Get replies of a comment
    List<Comment> findByParentId(Long parentId);
}
