package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.domain.author.Author;
import com.aluracursos.literalura.domain.author.DataAuthor;
import com.aluracursos.literalura.domain.book.Book;
import com.aluracursos.literalura.domain.book.DataBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bookrepository")
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByTitle(String bookname);

//    @Override
    Page<Book> findAll(Pageable pageable);

    Page<Book> findByTitleContainsIgnoreCase(Pageable pageable, String bookName);


    Page<Book> findTop10ByOrderByDownloadCountDesc(Pageable pageable);

    @Query("SELECT b FROM Book b INNER JOIN b.authors a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Book> findByAuthorsByAuthorName(Pageable pageable, String authorName);

    @Query("SELECT b FROM Book b Join b.authors a WHERE a.birthDate <= :endDate AND (a.deathDate IS NULL OR a.deathDate >= :startDate)")
    Page<Book> findByAuthorLife(Pageable pageable, Integer startDate, Integer endDate);

    @Query("SELECT b FROM Book b WHERE LOWER(b.languages) LIKE LOWER(?1)")
    Page<Book> findByLanguages(Pageable pageable, String language);


    @Query("SELECT b FROM Book b JOIN b.subjects s WHERE LOWER(s) LIKE LOWER(CONCAT('%', :subject, '%'))")
    Page<Book> findByTopics(Pageable pageable, String subject);

    Page<Book> findByCopyrightIs(Pageable pageable, Boolean option);

    Page<Author> findByAuthors(Pageable pageable, Author author);


}
