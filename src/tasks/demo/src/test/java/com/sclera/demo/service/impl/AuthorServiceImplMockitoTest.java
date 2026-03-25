package com.sclera.demo.service.impl;

import com.sclera.demo.dto.request.AuthorRequestDTO;
import com.sclera.demo.dto.response.AuthorResponseDTO;
import com.sclera.demo.entity.Author;
import com.sclera.demo.entity.Book;
import com.sclera.demo.repository.AuthorRepository;
import com.sclera.demo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplMockitoTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void createAuthorSavesMappedEntityAndReturnsResponse() {
        AuthorRequestDTO request = AuthorRequestDTO.builder()
                .firstName("Jane")
                .lastName("Austen")
                .country("UK")
                .build();

        when(authorRepository.save(any(Author.class))).thenAnswer(invocation -> {
            Author saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        AuthorResponseDTO response = authorService.createAuthor(request);

        assertEquals(1L, response.getId());
        assertEquals("Jane", response.getFirstName());
        assertEquals("Austen", response.getLastName());
        assertEquals("UK", response.getCountry());

        ArgumentCaptor<Author> captor = ArgumentCaptor.forClass(Author.class);
        verify(authorRepository).save(captor.capture());
        assertEquals("Jane", captor.getValue().getFirstName());
        assertEquals("Austen", captor.getValue().getLastName());
        assertEquals("UK", captor.getValue().getCountry());
        verifyNoInteractions(bookRepository);
    }

    @Test
    void deleteAuthorWithSingleAuthoredBookDeletesBookAndAuthor() {
        long authorId = 2L;
        Author existing = Author.builder()
                .id(authorId)
                .firstName("Leo")
                .lastName("Tolstoy")
                .country("Russia")
                .build();

        Book soloAuthoredBook = Book.builder()
                .id(101L)
                .name("War and Peace")
                .authors(new HashSet<>(Set.of(existing)))
                .build();

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existing));
        when(bookRepository.findDistinctByAuthors_Id(authorId)).thenReturn(List.of(soloAuthoredBook));

        AuthorResponseDTO response = authorService.deleteAuthor(authorId);

        assertEquals(authorId, response.getId());
        assertTrue(soloAuthoredBook.getAuthors().isEmpty());
        verify(bookRepository, never()).saveAll(anyList());
        verify(bookRepository).deleteAll(argThat(books -> {
            List<Book> captured = new ArrayList<>();
            books.forEach(captured::add);
            return captured.size() == 1 && captured.contains(soloAuthoredBook);
        }));
        verify(authorRepository).delete(existing);
    }

    @Test
    void deleteAuthorWithCoAuthoredBookDetachesAuthorAndKeepsBook() {
        long authorId = 3L;
        Author existing = Author.builder()
                .id(authorId)
                .firstName("Author")
                .lastName("One")
                .country("India")
                .build();

        Author coAuthor = Author.builder()
                .id(4L)
                .firstName("Author")
                .lastName("Two")
                .country("India")
                .build();

        Book coAuthoredBook = Book.builder()
                .id(202L)
                .name("Shared Book")
                .authors(new HashSet<>(Set.of(existing, coAuthor)))
                .build();

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existing));
        when(bookRepository.findDistinctByAuthors_Id(authorId)).thenReturn(List.of(coAuthoredBook));

        AuthorResponseDTO response = authorService.deleteAuthor(authorId);

        assertEquals(authorId, response.getId());
        assertEquals(1, coAuthoredBook.getAuthors().size());
        assertTrue(coAuthoredBook.getAuthors().stream().anyMatch(author -> author.getId().equals(coAuthor.getId())));
        verify(bookRepository).saveAll(argThat(books -> {
            List<Book> captured = new ArrayList<>();
            books.forEach(captured::add);
            return captured.size() == 1 && captured.getFirst().equals(coAuthoredBook);
        }));
        verify(bookRepository, never()).deleteAll(anyList());
        verify(authorRepository).delete(existing);
    }
}
