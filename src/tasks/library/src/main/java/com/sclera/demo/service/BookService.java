package com.sclera.demo.service;

import com.sclera.demo.dto.request.BookRequestDTO;
import com.sclera.demo.dto.response.BookResponseDTO;

import java.util.List;

public interface BookService {

    BookResponseDTO createBook(BookRequestDTO dto);
    BookResponseDTO updateBook(Long id , BookRequestDTO dto);
    BookResponseDTO deleteBook(Long id);
    List<BookResponseDTO> getAllBooks();
    BookResponseDTO getBookById(Long id);
}
