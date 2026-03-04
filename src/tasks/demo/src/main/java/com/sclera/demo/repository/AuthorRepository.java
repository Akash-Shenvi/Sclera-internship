package com.sclera.demo.repository;

import com.sclera.demo.entity.Author;
import com.sclera.demo.repository.projection.AuthorAvgBookPriceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface  AuthorRepository  extends JpaRepository<Author,Long> {
    @Query(value = """
            SELECT
                a.id AS authorId,
                CONCAT(a.first_name, ' ', a.last_name) AS fullName,
                a.country AS country,
                AVG(b.price) AS avgBookPrice
            FROM author a
            LEFT JOIN book_author ba ON a.id = ba.author_id
            LEFT JOIN book b ON ba.book_id = b.id
            GROUP BY a.id, a.first_name, a.last_name, a.country
            ORDER BY avgBookPrice ASC
            """, nativeQuery = true)
    List<AuthorAvgBookPriceProjection> findAuthorsWithAvgBookPriceAsc();

    @Query(value = """
            SELECT
                a.id AS authorId,
                CONCAT(a.first_name, ' ', a.last_name) AS fullName,
                a.country AS country,
                AVG(b.price) AS avgBookPrice
            FROM author a
            LEFT JOIN book_author ba ON a.id = ba.author_id
            LEFT JOIN book b ON ba.book_id = b.id
            GROUP BY a.id, a.first_name, a.last_name, a.country
            ORDER BY avgBookPrice DESC
            """, nativeQuery = true)
    List<AuthorAvgBookPriceProjection> findAuthorsWithAvgBookPriceDesc();
}
