package com.aluracursos.literalura.service;

import com.aluracursos.literalura.domain.book.Book;
import java.util.List;
import java.util.Optional;

public interface IWebSearchService {

    Optional<Book> webSearchAndSave(String bookName);

    String setSummary(List<String> summaries);
}
