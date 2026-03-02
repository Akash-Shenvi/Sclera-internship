package com.sclera.demo.controller;


import com.sclera.demo.dto.request.BookRequestDTO;
import com.sclera.demo.dto.response.BookResponseDTO;
import com.sclera.demo.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping()
    public ResponseEntity<BookResponseDTO>createBook(@RequestBody BookRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookService.createBook(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO>getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>>getAllBooks(){
        return ResponseEntity.ok(bookService.getAllBooks());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BookResponseDTO>deleteBook(@PathVariable Long id){
        return ResponseEntity.ok(bookService.deleteBook(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO>updateBook(@PathVariable Long id ,@RequestBody BookRequestDTO dto){
        return ResponseEntity.ok(bookService.updateBook(id, dto));
    }
}
