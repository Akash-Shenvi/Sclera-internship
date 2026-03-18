package com.sclera.demo.controller;

import com.sclera.demo.dto.external.ExternalBookResponseDTO;
import com.sclera.demo.service.ExternalBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/external-books")
public class ExternalBookController {

    private final ExternalBookService externalBookService;

    public ExternalBookController(ExternalBookService externalBookService) {
        this.externalBookService = externalBookService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ExternalBookResponseDTO>> searchExternalBooks(
            @RequestParam String query,
            @RequestParam(required = false) Integer limit
    ) {
        return ResponseEntity.ok(externalBookService.searchExternalBooks(query, limit));
    }
}
