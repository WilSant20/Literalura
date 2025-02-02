package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.domain.author.Author;
import com.aluracursos.literalura.domain.author.DataAuthor;
import com.aluracursos.literalura.domain.book.Book;
import com.aluracursos.literalura.domain.book.DataBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("bookrepository")
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainsIgnoreCase(String bookName);

    boolean existsByTitle(String bookname);

    List<Book> findTop10ByOrderByDownloadCountDesc();

    @Query("SELECT b FROM Book b INNER JOIN b.authors a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Book> findByAuthorsByAuthorName(String authorName);

    @Query("SELECT b FROM Book b Join b.authors a WHERE a.birthDate <= :endDate AND (a.deathDate IS NULL OR a.deathDate >= :startDate)")
    List<Book> findByAuthorLife(Integer startDate, Integer endDate);

    @Query("SELECT b FROM Book b WHERE LOWER(b.languages) LIKE LOWER(?1)")
    List<Book> findByLanguages(String language);


    @Query("SELECT b FROM Book b JOIN b.subjects s WHERE LOWER(s) LIKE LOWER(CONCAT('%', :subject, '%'))")
    List<Book> findByTopics(String subject);

    List<Book> findByCopyrightIs(Boolean option);

    List<Author> findByAuthors(Author author);


}
