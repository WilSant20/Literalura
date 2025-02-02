package com.aluracursos.literalura.service;

import com.aluracursos.literalura.domain.author.Author;
import com.aluracursos.literalura.domain.book.Book;
import com.aluracursos.literalura.domain.book.DataBook;
import com.aluracursos.literalura.repository.AuthorRepository;
import com.aluracursos.literalura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional // Asegura que todas las operaciones sean atómicas
public class WebSearchService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    @Qualifier("apiuse")
    private ExternalApiService externalApiService;


    /**
     * Procesa la búsqueda de un libro en la API y maneja la lógica de guardado.
     *
     * @param bookName Nombre del libro que se desea buscar.
     * @return El libro guardado o vacío si ya existe.
     */
    public Optional<Book> webSearchAndSave(String bookName) {
        // Consumir API y obtener los datos del libro
        DataBook dataBook = externalApiService.apiProcessedConsuption(bookName);
        if (dataBook == null) {
            return Optional.empty();
        }

        Book newBook = new Book(dataBook);

        // Verificar si el libro ya existe en la base de datos
        if (bookRepository.existsByTitle(newBook.getTitle())) {
            return Optional.empty();
        }

        // Procesar autores asociados al libro
        List<Author> authors = new ArrayList<>();
        for (Author author : newBook.getAuthors()) {
            // Buscar autor en la base de datos
            Optional<Author> existingAuthor = authorRepository.findByName(
                    author.getName());

            if (existingAuthor.isPresent()) {
                // Si el autor ya existe, usar el existente
                authors.add(existingAuthor.get());
            } else {
                // Si el autor no existe, guardar el nuevo autor
                Author savedAuthor = authorRepository.save(author);
                authors.add(savedAuthor);
            }
        }

        newBook.setAuthors(authors);

        Book savedBook = bookRepository.save(newBook);
        return Optional.of(savedBook);
    }
}