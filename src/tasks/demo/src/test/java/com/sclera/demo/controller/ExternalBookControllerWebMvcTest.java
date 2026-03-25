package com.sclera.demo.controller;

import com.sclera.demo.dto.external.ExternalBookResponseDTO;
import com.sclera.demo.exception.GlobalExceptionHandler;
import com.sclera.demo.service.ExternalBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExternalBookController.class)
@Import(GlobalExceptionHandler.class)
class ExternalBookControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ExternalBookService externalBookService;

    @Test
    void searchExternalBooksReturnsMappedResponse() throws Exception {
        ExternalBookResponseDTO response = ExternalBookResponseDTO.builder()
                .title("The Lord of the Rings")
                .authors(List.of("J. R. R. Tolkien"))
                .firstPublishYear(1954)
                .isbn("9780261102385")
                .source("OPEN_LIBRARY")
                .build();

        when(externalBookService.searchExternalBooks("lord", 5)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/external-books/search")
                        .param("query", "lord")
                        .param("limit", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("The Lord of the Rings"))
                .andExpect(jsonPath("$[0].authors[0]").value("J. R. R. Tolkien"))
                .andExpect(jsonPath("$[0].firstPublishYear").value(1954))
                .andExpect(jsonPath("$[0].isbn").value("9780261102385"))
                .andExpect(jsonPath("$[0].source").value("OPEN_LIBRARY"));
    }
}
