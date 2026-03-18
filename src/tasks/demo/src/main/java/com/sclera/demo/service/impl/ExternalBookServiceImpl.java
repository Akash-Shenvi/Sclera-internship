package com.sclera.demo.service.impl;

import com.sclera.demo.client.OpenLibraryClient;
import com.sclera.demo.dto.external.ExternalBookResponseDTO;
import com.sclera.demo.dto.external.OpenLibraryDocDTO;
import com.sclera.demo.dto.external.OpenLibrarySearchResponseDTO;
import com.sclera.demo.service.ExternalBookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExternalBookServiceImpl implements ExternalBookService {

    private static final String SOURCE = "OPEN_LIBRARY";

    private final OpenLibraryClient openLibraryClient;
    private final int defaultLimit;
    private final int maxLimit;

    public ExternalBookServiceImpl(
            OpenLibraryClient openLibraryClient,
            @Value("${openlibrary.default-limit}") int defaultLimit,
            @Value("${openlibrary.max-limit}") int maxLimit
    ) {
        this.openLibraryClient = openLibraryClient;
        this.defaultLimit = defaultLimit;
        this.maxLimit = maxLimit;
    }

    @Override
    public List<ExternalBookResponseDTO> searchExternalBooks(String query, Integer limit) {
        String normalizedQuery = query == null ? "" : query.trim();
        if (normalizedQuery.isEmpty()) {
            throw new IllegalArgumentException("Query must not be blank.");
        }

        int resolvedLimit = resolveLimit(limit);
        OpenLibrarySearchResponseDTO response = openLibraryClient.searchBooks(normalizedQuery, resolvedLimit);
        List<OpenLibraryDocDTO> docs = response == null ? List.of() : Optional.ofNullable(response.getDocs()).orElse(List.of());

        return docs.stream()
                .map(this::mapToExternalBook)
                .toList();
    }

    private int resolveLimit(Integer limit) {
        if (limit == null) {
            return defaultLimit;
        }
        if (limit < 1) {
            return 1;
        }
        return Math.min(limit, maxLimit);
    }

    private ExternalBookResponseDTO mapToExternalBook(OpenLibraryDocDTO doc) {
        List<String> authors = Optional.ofNullable(doc.getAuthorName()).orElse(List.of());
        List<String> isbns = Optional.ofNullable(doc.getIsbn()).orElse(List.of());
        String primaryIsbn = isbns.isEmpty() ? null : isbns.getFirst();

        return ExternalBookResponseDTO.builder()
                .title(doc.getTitle())
                .authors(authors)
                .firstPublishYear(doc.getFirstPublishYear())
                .isbn(primaryIsbn)
                .source(SOURCE)
                .build();
    }
}
