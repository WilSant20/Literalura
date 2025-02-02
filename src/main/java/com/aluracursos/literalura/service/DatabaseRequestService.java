package com.aluracursos.literalura.service;

import com.aluracursos.literalura.domain.author.Author;
import com.aluracursos.literalura.domain.author.DataAuthor;
import com.aluracursos.literalura.domain.book.Book;
import com.aluracursos.literalura.domain.book.DataBook;
import com.aluracursos.literalura.domain.book.Languages;
import com.aluracursos.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("dbservice")
@Transactional
public class DatabaseRequestService {
    @Autowired
    private BookRepository bookRepository;


    public Optional<List<Book>> findByAuthor(String authorName) {
        List<Book> books = bookRepository.findByAuthorsByAuthorName(authorName);
//        List<Book> books = new ArrayList<>();
//        for (DataBook dataBook : dataBooksList) {
//            books.add(new Book(dataBook));
//            }
        System.out.println(books);
        return books.isEmpty() ? Optional.empty() : Optional.of(books);
    }

    public Optional<List<Book>> searchBookByName(String name) {
        List<Book> bookList = bookRepository.findByTitleContainsIgnoreCase(name);
        return bookList.isEmpty() ? Optional.empty() : Optional.of(bookList);
    }


    public Optional<List<Book>> searchTop10PopularBooks() {
        List<Book> top10 = bookRepository.findTop10ByOrderByDownloadCountDesc();
        return top10.isEmpty() ? Optional.empty() : Optional.of(top10);
    }


    public Optional<List<Book>> searchBooksByTopic(String topic) {
        List<Book> bookList = bookRepository.findByTopics(topic);
        return bookList.isEmpty() ? Optional.empty() : Optional.of(bookList);
    }


    public Optional<Book> searchBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Optional<List<Book>> searchBooksByLanguage(Languages language) {
        List<Book> bookList = bookRepository.findByLanguages(language.toString());
        return bookList.isEmpty() ? Optional.empty() : Optional.of(bookList);
    }


    public Optional<List<Book>> listAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        return bookList.isEmpty() ? Optional.empty() : Optional.of(bookList);
    }


    public Optional<List<Book>> findAuthorInRange(Integer birthDate, Integer deathDate) {
        List<Book> bookList = bookRepository.findByAuthorLife(birthDate, deathDate);
        return bookList.isEmpty() ? Optional.empty() : Optional.of(bookList);
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


    public Optional<List<Book>> searchCopyright(Boolean option) {
        List<Book> bookList = bookRepository.findByCopyrightIs(option);
        return bookList.isEmpty() ? Optional.empty() : Optional.of(bookList);
    }
}
