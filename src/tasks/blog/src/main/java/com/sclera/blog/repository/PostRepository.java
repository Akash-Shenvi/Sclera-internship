package com.sclera.blog.repository;

import com.sclera.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Get posts by user
    List<Post> findByUserId(Long userId);
    Page<Post> findByUserId(Long userId, Pageable pageable);
    Page<Post> findByUserIdAndPublishedTrue(Long userId, Pageable pageable);

    // Get only published posts
    List<Post> findByPublishedTrue();

    // Get published posts + current user's own posts (including drafts)
    Page<Post> findByPublishedTrueOrUserId(Long userId, Pageable pageable);

    @Query("""
            select p from Post p
            where lower(p.title) like lower(concat('%', :query, '%'))
              and (p.published = true or p.user.id = :currentUserId)
            """)
    Page<Post> searchVisiblePostsByTitle(
            @Param("query") String query,
            @Param("currentUserId") Long currentUserId,
            Pageable pageable
    );
}
