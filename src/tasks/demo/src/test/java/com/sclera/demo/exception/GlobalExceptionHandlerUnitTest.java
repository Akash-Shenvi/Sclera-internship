package com.sclera.demo.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerUnitTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFoundReturns404AndErrorBody() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/authors/99");

        ResponseEntity<ApiErrorResponse> response = handler.handleNotFound(
                new ResourceNotFoundException("Author not found with id: 99"),
                request
        );

        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getTimestamp());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Not Found", response.getBody().getError());
        assertEquals("Author not found with id: 99", response.getBody().getMessage());
        assertEquals("/api/authors/99", response.getBody().getPath());
    }

    @Test
    void handleBadRequestReturns400AndErrorBody() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/books/page");

        ResponseEntity<ApiErrorResponse> response = handler.handleBadRequest(
                new IllegalArgumentException("Page size must be positive"),
                request
        );

        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getTimestamp());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Bad Request", response.getBody().getError());
        assertEquals("Page size must be positive", response.getBody().getMessage());
        assertEquals("/api/books/page", response.getBody().getPath());
    }
}
