package com.sclera.demo.controller;

import com.sclera.demo.dto.request.AuthorRequestDTO;
import com.sclera.demo.dto.response.AuthorResponseDTO;
import com.sclera.demo.exception.GlobalExceptionHandler;
import com.sclera.demo.exception.ResourceNotFoundException;
import com.sclera.demo.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@Import(GlobalExceptionHandler.class)
class AuthorControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthorService authorService;

    @Test
    void createAuthorReturns201AndResponseBody() throws Exception {
        AuthorResponseDTO response = AuthorResponseDTO.builder()
                .id(10L)
                .firstName("George")
                .lastName("Orwell")
                .country("UK")
                .build();

        when(authorService.createAuthor(any(AuthorRequestDTO.class))).thenReturn(response);

        String jsonBody = """
                {
                  "firstName": "George",
                  "lastName": "Orwell",
                  "country": "UK"
                }
                """;

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.firstName").value("George"))
                .andExpect(jsonPath("$.lastName").value("Orwell"))
                .andExpect(jsonPath("$.country").value("UK"));
    }

    @Test
    void getAuthorByIdWhenMissingReturns404ErrorPayload() throws Exception {
        when(authorService.getAuthorById(99L))
                .thenThrow(new ResourceNotFoundException("Author not found with id: 99"));

        mockMvc.perform(get("/api/authors/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Author not found with id: 99"))
                .andExpect(jsonPath("$.path").value("/api/authors/99"));
    }

    @Test
    void searchAuthorsReturnsMatchingAuthors() throws Exception {
        AuthorResponseDTO response = AuthorResponseDTO.builder()
                .id(11L)
                .firstName("Jane")
                .lastName("Austen")
                .country("UK")
                .build();

        when(authorService.searchAuthors("jane")).thenReturn(List.of(response));

        mockMvc.perform(get("/api/authors/search")
                        .param("query", "jane"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(11))
                .andExpect(jsonPath("$[0].firstName").value("Jane"))
                .andExpect(jsonPath("$[0].lastName").value("Austen"))
                .andExpect(jsonPath("$[0].country").value("UK"));
    }
}
