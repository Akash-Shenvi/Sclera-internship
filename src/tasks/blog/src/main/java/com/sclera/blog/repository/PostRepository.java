package com.sclera.blog.repository;

import com.sclera.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Get posts by user
    List<Post> findByUserId(Long userId);

    // Get only published posts
    List<Post> findByPublishedTrue();

    // Get published posts + current user's own posts (including drafts)
    List<Post> findByPublishedTrueOrUserId(Long userId);
}
