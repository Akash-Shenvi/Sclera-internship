package com.sclera.demo.repository;

import com.sclera.demo.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  AuthorRepository  extends JpaRepository<Author,Long> {
}
