package com.aluracursos.literalura.controllers;

import com.aluracursos.literalura.domain.book.Book;
import com.aluracursos.literalura.repository.BookRepository;
import com.aluracursos.literalura.service.WebSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController("eapicontroller")
@RequestMapping("/web")
public class ExternalApiController {

    @Autowired
    private WebSearchService wsservice;
    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/{bookname}")
    public ResponseEntity<?> externalSearch(@RequestParam String bookname) {
        Optional<Book> book = wsservice.webSearchAndSave(bookname);
        if (book.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("Libro no encontrado");
        } else {
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/search/books/id/{id}")
                    .buildAndExpand(book.get().getId())
                    .toUri();
            return ResponseEntity.ok(book);
        }
    }
}
