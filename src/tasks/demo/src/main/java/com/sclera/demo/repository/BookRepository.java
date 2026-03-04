package com.sclera.demo.repository;

import com.sclera.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository <Book,Long> {
    @Query("select avg(b.rating) from Book b")
    Double findAverageRating();

    boolean existsByAuthors_Id(Long authorId);
}
