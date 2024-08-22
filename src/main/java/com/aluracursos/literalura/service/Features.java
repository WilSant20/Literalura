package com.aluracursos.literalura.service;

import com.aluracursos.literalura.domain.author.Author;
import com.aluracursos.literalura.domain.author.DataAuthor;
import com.aluracursos.literalura.domain.book.Book;
import com.aluracursos.literalura.domain.book.BookRepository;
import com.aluracursos.literalura.domain.book.DataBook;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class Features {

    private final String URL = "https://gutendex.com/books/?search=";
    private final Scanner keyboard = new Scanner(System.in);
    private final ApiUse apiUse = new ApiUse();

//    @Autowired
    private BookRepository repository;

    @Autowired
    public Features(BookRepository repository) {
        this.repository = repository;
    }

    public Features() {
    }

    private DataBook searchWeb() {
        System.out.println("Escriba el nombre del libro que desea buscar");
        var bookName = keyboard.nextLine().toLowerCase();
        var json = apiUse.getData(URL + bookName.replace(" ", "%20"));
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(json);
            JsonNode results = root.path("results");
            if (results.isArray() && results.size() > 0) {
                JsonNode firstResult = results.get(0);
                DataBook data = mapper.treeToValue(firstResult, DataBook.class);
//                System.out.println("Texto mapeado: " + data);
                return data;
            } else {
                System.out.println("""
                        ---------------------------------
                        No se encontraron resultados
                        ---------------------------------""");
                return null;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public String SearchAndSaveWebBook() {
        DataBook data = searchWeb();
        if (data != null) {
            List<Author> authors = new ArrayList<>();
            for (DataAuthor dataAuthor : data.authors()) {
                Optional<Author> existingAuthor = findAuthor(dataAuthor.name(), dataAuthor.birthDate(), dataAuthor.deathDate());
                Author author = existingAuthor.orElseGet(() -> new Author(dataAuthor.name(), dataAuthor.birthDate(), dataAuthor.deathDate()));
                if (existingAuthor.isEmpty()) {
                    authors.add(author);
                }
            }

            Book book = new Book(data);
            if (repository.findAll().contains(book)) {
                System.out.println("Ese libro ya esta en la base de datos");
            } else {
                repository.save(book);
                System.out.println("\nEl libro Encontrado y guardado: " + book.toString());
            }
            return "Accion completada";
        } else {
            return "No se pudo guardar el libro porque no se encontraron datos.";
        }
    }

    @Transactional
    private Optional<Author> findAuthor(String name, Integer birthDate, Integer deathDate) {
        List<Book> allBooks = repository.findAll();
        for (Book book : allBooks) {
            for (Author author : book.getAuthors()) {
                if (author.getName().equals(name) && author.getBirthDate().equals(birthDate) && author.getDeathDate().equals(deathDate)) {
                    return Optional.of(author);
                }
            }
        }
        return Optional.empty();
    }

    @Transactional
    public void searchBookByName() {
        System.out.println("¿Qué serie en el repositorio quieres buscar?");
        var bookName = keyboard.nextLine();
        var bookSearch = repository.findByTitleContainsIgnoreCase(bookName);
        if (bookSearch.isEmpty()) {
            System.out.println("""
        *****************************************
                  Serie no encontrada
        *****************************************""");
        } else {
            System.out.println("Resultados: " + bookSearch.toString());
        }

    }

    @Transactional
    public void searchTop10PopularBooks() {
        var top10 = repository.findTop10ByOrderByDownloadCountDesc();
        System.out.println("Resultados: " + top10.toString());
    }

    @Transactional
    public void searchBooksByTopic() {
        System.out.println("¿Qué temas o género estas buscando?");
        var search = keyboard.nextLine();
        var answer = repository.findByTopics(search);
        System.out.println("Resultado: " +  answer.toString());

    }

    @Transactional
    public void searchBookById() {
        System.out.println("Escriba el ID del libro que desea buscar");
        var bookId = keyboard.nextLong();
        var bookSearch = repository.findById(bookId);
        System.out.println("Resultados: " + bookSearch.toString());
    }

    @Transactional
    public void searchBooksByAuthor() {
        System.out.println("¿Qué Autor deseas buscar?");
        String search = keyboard.nextLine();
        var author = repository.findByAuthorsByAuthorName(search);
        System.out.println("Resultados: "+ author.toString());
    }

    @Transactional
    public void searchBooksByLanguage() {
        System.out.println("¿Qué lenguaje estas buscando? \nEscribe el numero que corresponda");
        var option = -1;
        System.out.println("""
                -------------------------------
                
                1   -   Inglés
                2   -   Español
                3   -   Francés
                4   -   Portugués
                5   -   Italiano
                
                -------------------------------""");

        List<Book> search;
        option = keyboard.nextInt();
        keyboard.nextLine();
        switch (option) {
            case 1:
                search = repository.findByLanguages("ENGLISH");
                System.out.println("Resultados: " + search);
                break;
            case 2:
                search = repository.findByLanguages("SPANISH");
                System.out.println("Resultados: " + search);
                break;
            case 3:
                search = repository.findByLanguages("FRENCH");
                System.out.println("Resultados: " + search);
                break;
            case 4:
                search = repository.findByLanguages("PORTUGUESE");
                System.out.println("Resultados: " + search);
                break;
            case 5:
                search = repository.findByLanguages("ITALIAN");
                System.out.println("Resultados: " + search);
                break;
            default:
                System.out.println("Opción inválida");
        }
    }

    @Transactional
    public void listAllBooks() {
        var books = repository.findAll();
        System.out.println("""
                Buscando todos los libros disponibles ...
                Resultados: """ + books.toString());
    }

    @Transactional
    public void findAuthorInRange() {
        System.out.println("""
                +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                
                Busqueda de libros por año rango de tiempo donde estuvo vivo el autor
                ---------------------------------------------------------------------
                
                Escriba el año donde desea empezar a buscar""");
        var dateOfBirth = keyboard.nextInt();
        System.out.println("Escriba el año donde desea finalizar de buscar");
        var dateOfDeath = keyboard.nextInt();
        var results = repository.findByAuthorLive(dateOfBirth, dateOfDeath);
        System.out.println("Resultados: " + results.toString());

    }

    //Reorder all author's names, saved in the repository, to the preferred format
    @Transactional
    public void updateAuthorNames() {
        List<Book> allBooks = repository.findAll();
        for (Book book : allBooks) {
            for (Author author : book.getAuthors()) {
                String reorderedName = reorderName(author.getName());
                author.setName(reorderedName);
                repository.saveAll(allBooks);

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

    @Transactional
    public void searchCopyright() {
        System.out.println("""
                ----------------------------------------------------------------------------
                                     Seleccione la opcion adecuada
                1 - Copyright Sí -
                2 - Copyright No -
                ----------------------------------------------------------------------------
                """);
        var option = -1;
        option = keyboard.nextInt();
        switch (option) {
            case 1:
                List<Book> books = repository.findByCopyrightIs(Boolean.TRUE);
                System.out.println("Resultados: " + books.toString());
                break;
            case 2:
                List<Book> books1 = repository.findByCopyrightIs(Boolean.FALSE);
                System.out.println("Resultados: " + books1.toString());
                break;
            default:
                System.out.println("Opción inválida, elija nuevamente");
        }

    }

}
