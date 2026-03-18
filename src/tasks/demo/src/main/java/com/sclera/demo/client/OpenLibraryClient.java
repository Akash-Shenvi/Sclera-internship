package com.sclera.demo.client;

import com.sclera.demo.dto.external.OpenLibrarySearchResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Component
public class OpenLibraryClient {

    private final WebClient openLibraryWebClient;
    private final String searchPath;

    public OpenLibraryClient(
            @Qualifier("openLibraryWebClient") WebClient openLibraryWebClient,
            @Value("${openlibrary.search-path}") String searchPath
    ) {
        this.openLibraryWebClient = openLibraryWebClient;
        this.searchPath = searchPath;
    }

    public OpenLibrarySearchResponseDTO searchBooks(String query, int limit) {
        return openLibraryWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(searchPath)
                        .queryParam("q", query)
                        .queryParam("limit", limit)
                        .build())
                .retrieve()
                .bodyToMono(OpenLibrarySearchResponseDTO.class)
                .timeout(Duration.ofSeconds(5))
                .onErrorReturn(OpenLibrarySearchResponseDTO.builder()
                        .numFound(0)
                        .docs(List.of())
                        .build())
                .block();
    }
}
