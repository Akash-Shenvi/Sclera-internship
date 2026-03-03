package com.sclera.demo.controller;

import com.sclera.demo.dto.request.AuthorRequestDTO;
import com.sclera.demo.dto.response.AuthorResponseDTO;
import com.sclera.demo.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> createAuthor(@RequestBody AuthorRequestDTO dto){
        AuthorResponseDTO created =authorService.createAuthor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO>getAuthorById(@PathVariable Long id){
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping()
    public ResponseEntity<List<AuthorResponseDTO>>getAuthors(){
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO>deleteAuthor(@PathVariable Long id){
        return ResponseEntity.ok(authorService.deleteAuthor(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO>updateAuthor(@PathVariable Long id, @RequestBody AuthorRequestDTO dto){
        return ResponseEntity.ok(authorService.updateAuthor(id,dto));
    }
}
