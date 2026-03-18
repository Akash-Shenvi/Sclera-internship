package com.sclera.demo.service;

import com.sclera.demo.dto.external.ExternalBookResponseDTO;

import java.util.List;

public interface ExternalBookService {

    List<ExternalBookResponseDTO> searchExternalBooks(String query, Integer limit);
}
