package com.sclera.demo.repository;

import com.sclera.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository <Book,Long> {
    boolean existsByAuthors_Id(Long authorId);
    List<Book> findDistinctByAuthors_Id(Long authorId);
}
