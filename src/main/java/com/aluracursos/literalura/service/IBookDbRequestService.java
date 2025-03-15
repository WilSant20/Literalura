package com.aluracursos.literalura.service;

import com.aluracursos.literalura.domain.author.Author;
import com.aluracursos.literalura.domain.book.Book;
import com.aluracursos.literalura.domain.book.Languages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface IBookDbRequestService {
    Page<Book> findByAuthor(Pageable pageable, String authorName);

    Page<Book> searchBookByName(Pageable pageable, String name);


    Page<Book> searchTop10PopularBooks(Pageable pageable);


    Page<Book> searchBooksByTopic(Pageable pageable, String topic);


    Optional<Book> searchBookById(Long id);

    Page<Book> searchBooksByLanguage(Pageable pageable, String language);


    Page<Book> listAllBooks(Pageable pageable);


    Page<Book> findAuthorInRange(Pageable pageable, Integer birthDate, Integer deathDate);

    Page<Book> searchCopyright(Pageable pageable, Boolean option);


    //Reorder all author's names, saved in the repository, to the preferred format

    void updateAuthorNames();
}
