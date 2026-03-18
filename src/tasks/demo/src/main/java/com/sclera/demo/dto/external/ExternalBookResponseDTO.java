package com.sclera.demo.dto.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalBookResponseDTO {

    private String title;
    private List<String> authors;
    private Integer firstPublishYear;
    private String isbn;
    private String source;
}
