package com.sclera.demo.service.impl;

import com.sclera.demo.dto.request.BookRequestDTO;
import com.sclera.demo.dto.response.AuthorResponseDTO;
import com.sclera.demo.dto.response.BookResponseDTO;
import com.sclera.demo.entity.Author;
import com.sclera.demo.repository.AuthorRepository;
import com.sclera.demo.repository.BookRepository;
import com.sclera.demo.service.BookService;
import org.springframework.stereotype.Service;

import com.sclera.demo.entity.Book;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    @Override
    public BookResponseDTO createBook(BookRequestDTO dto) {

       Set<Author> authors =authorRepository.findAllById(dto.getAuthorIds())
               .stream()
               .collect(Collectors.toSet());

        Book book =Book.builder()
                .name(dto.getName())
                .isbnNumber(dto.getIsbnNumber())
                .category(dto.getCategory())
                .rating(dto.getRating())
                .price(dto.getPrice())
                .authors(authors)
                .build();
        return mapToResponse(bookRepository.save(book));
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow();

        Set<Author> authors=authorRepository.findAllById(dto.getAuthorIds()).stream().collect(Collectors.toSet());
        book.setName(dto.getName());
        book.setIsbnNumber(dto.getIsbnNumber());
        book.setCategory(dto.getCategory());
        book.setRating(dto.getRating());
        book.setPrice(dto.getPrice());
        book.setAuthors(authors);
        return mapToResponse(bookRepository.save(book));
    }

    @Override
    public BookResponseDTO deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow();

        BookResponseDTO response = mapToResponse(book);
        bookRepository.delete(book);
        return response;
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());

    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        Book book =bookRepository.findById(id)
                .orElseThrow();

        return mapToResponse(book);
    }

    private BookResponseDTO mapToResponse (Book book){
        return BookResponseDTO.builder()
                .id(book.getId())
                .isbnNumber(book.getIsbnNumber())
                .name(book.getName())
                .category(book.getCategory())
                .rating(book.getRating())
                .price(book.getPrice())
                .authors(
                        book.getAuthors()
                                .stream()
                                .map(a -> AuthorResponseDTO.builder()
                                        .id(a.getId())
                                        .firstName(a.getFirstName())
                                        .lastName(a.getLastName())
                                        .country(a.getCountry())
                                        .build())
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
