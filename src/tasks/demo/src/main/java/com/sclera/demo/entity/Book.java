package com.sclera.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "isbn_number", unique = true)
    private String isbnNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "category")
    private String category;
    @Column(name = "rating")
    private double rating;
    @Column(name = "price")
    private double price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name="author_id")
    )
    @Builder.Default
    private Set<Author> authors =new HashSet<>();
}
