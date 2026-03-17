package com.sclera.demo.service.impl;

import com.sclera.demo.dto.request.AuthorRequestDTO;
import com.sclera.demo.dto.response.AuthorAvgBookPriceResponseDTO;
import com.sclera.demo.dto.response.AuthorAvgRatingResponseDTO;
import com.sclera.demo.dto.response.AuthorResponseDTO;
import com.sclera.demo.entity.Author;
import com.sclera.demo.entity.Book;
import com.sclera.demo.exception.ResourceNotFoundException;
import com.sclera.demo.repository.AuthorRepository;
import com.sclera.demo.repository.BookRepository;
import com.sclera.demo.repository.projection.AuthorAvgBookPriceProjection;
import com.sclera.demo.repository.projection.AuthorAvgRatingProjection;
import com.sclera.demo.service.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }


    @Override
    public AuthorResponseDTO createAuthor(AuthorRequestDTO dto) {
        Author author =Author.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .country(dto.getCountry())
                .build();
        return mapToResponse(authorRepository.save(author));
    }

    @Override
    public AuthorResponseDTO updateAuthor(Long id, AuthorRequestDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setCountry(dto.getCountry());
        Author updated=authorRepository.save(author);
        return mapToResponse(updated);
    }

    @Override
    public List<AuthorResponseDTO> getAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorResponseDTO> searchAuthors(String query) {
        String normalizedQuery = query == null ? "" : query.trim();
        if (normalizedQuery.isEmpty()) {
            return getAllAuthors();
        }

        return authorRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrCountryContainingIgnoreCase(
                        normalizedQuery,
                        normalizedQuery,
                        normalizedQuery
                )
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorResponseDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return mapToResponse(author);
    }

    @Override
    @Transactional
    public AuthorResponseDTO deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        List<Book> linkedBooks = bookRepository.findDistinctByAuthors_Id(id);
        List<Book> booksToDelete = new ArrayList<>();
        List<Book> booksToUpdate = new ArrayList<>();

        for (Book book : linkedBooks) {
            book.getAuthors().removeIf(linkedAuthor -> id.equals(linkedAuthor.getId()));
            if (book.getAuthors().isEmpty()) {
                booksToDelete.add(book);
            } else {
                booksToUpdate.add(book);
            }
        }

        if (!booksToUpdate.isEmpty()) {
            bookRepository.saveAll(booksToUpdate);
        }

        if (!booksToDelete.isEmpty()) {
            bookRepository.deleteAll(booksToDelete);
        }

        authorRepository.delete(author);
        return mapToResponse(author);
    }

    @Override
    public List<AuthorAvgRatingResponseDTO> getAverageRatingOfAllAuthors() {
        return authorRepository.findAverageRatingOfAllAuthors()
                .stream()
                .map(this::mapToAvgRatingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuthorAvgBookPriceResponseDTO> getAuthorsWithAvgBookPrice(String sort) {
        boolean isDesc = "desc".equalsIgnoreCase(sort);
        List<AuthorAvgBookPriceProjection> data = isDesc
                ? authorRepository.findAuthorsWithAvgBookPriceDesc()
                : authorRepository.findAuthorsWithAvgBookPriceAsc();

        return data.stream()
                .map(this::mapToAvgBookPriceResponse)
                .collect(Collectors.toList());
    }

    private AuthorResponseDTO mapToResponse(Author author){
        return AuthorResponseDTO.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .country(author.getCountry())
                .build();
    }

    private AuthorAvgBookPriceResponseDTO mapToAvgBookPriceResponse(AuthorAvgBookPriceProjection projection){
        return AuthorAvgBookPriceResponseDTO.builder()
                .authorId(projection.getAuthorId())
                .fullName(projection.getFullName())
                .country(projection.getCountry())
                .avgBookPrice(projection.getAvgBookPrice())
                .build();
    }

    private AuthorAvgRatingResponseDTO mapToAvgRatingResponse(AuthorAvgRatingProjection projection){
        return AuthorAvgRatingResponseDTO.builder()
                .authorId(projection.getAuthorId())
                .fullName(projection.getFullName())
                .country(projection.getCountry())
                .avgRating(projection.getAvgRating() == null ? 0.0 : projection.getAvgRating())
                .build();
    }
}
