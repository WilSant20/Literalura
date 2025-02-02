package com.aluracursos.literalura.service;

import com.aluracursos.literalura.domain.author.Author;
import com.aluracursos.literalura.domain.book.Book;
import com.aluracursos.literalura.domain.book.Languages;
import com.aluracursos.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service("dbservice")
@Transactional
public class DatabaseRequestService {
    @Autowired
    private BookRepository bookRepository;


    public Page<Book> findByAuthor(Pageable pageable, String authorName) {
        return bookRepository.findByAuthorsByAuthorName(pageable, authorName);
    }

    public Page<Book> searchBookByName(Pageable pageable, String name) {
        return bookRepository.findByTitleContainsIgnoreCase(pageable, name);
    }


    public Page<Book> searchTop10PopularBooks(Pageable pageable) {
        return bookRepository.findTop10ByOrderByDownloadCountDesc(pageable);
    }


    public Page<Book> searchBooksByTopic(Pageable pageable, String topic) {
        return bookRepository.findByTopics(pageable, topic);
    }


    public Optional<Book> searchBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Page<Book> searchBooksByLanguage(Pageable pageable, String language) {
        boolean isValidLanguage = Arrays.stream(Languages.values())
                .map(Enum::toString)
                .anyMatch(lang -> lang.equalsIgnoreCase(language));

        if (!isValidLanguage) {
            return Page.empty();
        }

        return bookRepository.findByLanguages(pageable, language);
    }


    public Page<Book> listAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }


    public Page<Book> findAuthorInRange(Pageable pageable, Integer birthDate, Integer deathDate) {
        return bookRepository.findByAuthorLife(pageable, birthDate, deathDate);
    }

    public Page<Book> searchCopyright(Pageable pageable, Boolean option) {
        return bookRepository.findByCopyrightIs(pageable, option);
    }


    //Reorder all author's names, saved in the repository, to the preferred format

    public void updateAuthorNames() {
        List<Book> allBooks = bookRepository.findAll();
        for (Book book : allBooks) {
            for (Author author : book.getAuthors()) {
                String reorderedName = reorderName(author.getName());
                author.setName(reorderedName);
                bookRepository.saveAll(allBooks);

            }
        }
    }


    //Clean, order and update the author's name that comes from the array

    private String reorderName(String name) {
        String remove = name;
        String[] parts = name.split(", ");
        if (parts.length == 2) {
            return parts[1].replaceAll("\\(.*?\\)", "") + parts[0].replaceAll("\\(.*?\\)", ""); // Reorder "Name Lastname" format, and clean any other information in "()"
        } else {
            return name.replaceAll("\\(.*?\\)", ""); // If the format is correct, just clean any other information
        }
    }
}
