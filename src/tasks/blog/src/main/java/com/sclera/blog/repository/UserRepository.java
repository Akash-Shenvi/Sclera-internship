package com.sclera.blog.repository;

import com.sclera.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Used for login
    Optional<User> findByEmail(String email);

    // Check duplicate email during registration
    boolean existsByEmail(String email);
}