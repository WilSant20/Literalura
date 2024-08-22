package com.aluracursos.literalura.domain.book;

import com.aluracursos.literalura.domain.author.DataAuthor;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<DataAuthor> authors,
        @JsonAlias("subjects") List<String> subjects,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("copyright") boolean copyright,
        @JsonAlias("download_count") Integer downloadCount
) {
}
