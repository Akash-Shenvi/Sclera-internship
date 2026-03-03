package com.sclera.demo.service.impl;

import com.sclera.demo.dto.request.AuthorRequestDTO;
import com.sclera.demo.dto.response.AuthorResponseDTO;
import com.sclera.demo.entity.Author;
import com.sclera.demo.repository.AuthorRepository;
import com.sclera.demo.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
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
        Author author=authorRepository.findById(id).orElseThrow();
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
    public AuthorResponseDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return mapToResponse(author);
    }

    @Override
    public AuthorResponseDTO deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        authorRepository.delete(author);
        return mapToResponse(author);
    }

    private AuthorResponseDTO mapToResponse(Author author){
        return AuthorResponseDTO.builder()
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .country(author.getCountry())
                .build();
    }
}
