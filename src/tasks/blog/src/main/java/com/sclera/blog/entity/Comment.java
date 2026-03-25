package com.sclera.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    // Many comments → one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Many comments → one post
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // 🔥 SELF-REFERENCE (parent comment)
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    // 🔥 CHILD COMMENTS (replies)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies;
}