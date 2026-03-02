package com.sclera.demo.service;

import com.sclera.demo.dto.request.AuthorRequestDTO;
import com.sclera.demo.dto.response.AuthorResponseDTO;

import java.util.List;

public interface AuthorService {

    AuthorResponseDTO createAuthor(AuthorRequestDTO dto);
    AuthorResponseDTO updateAuthor(Long id,AuthorRequestDTO dto);
    AuthorResponseDTO deleteAuthor(Long id);
    AuthorResponseDTO getAuthorById(Long id);
    List<AuthorResponseDTO>getAllAuthors();
}
