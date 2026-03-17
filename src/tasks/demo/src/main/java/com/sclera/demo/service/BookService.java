package com.sclera.demo.service;

import com.sclera.demo.dto.request.BookRequestDTO;
import com.sclera.demo.dto.response.BookResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    BookResponseDTO createBook(BookRequestDTO dto);
    BookResponseDTO updateBook(Long id , BookRequestDTO dto);
    BookResponseDTO deleteBook(Long id);
    List<BookResponseDTO> getAllBooks();
    List<BookResponseDTO> searchBooks(String query);
    Page<BookResponseDTO> getBooksPage(int page, int size, String sort);
    BookResponseDTO getBookById(Long id);
}
