package com.aluracursos.literalura.controllers;

import com.aluracursos.literalura.domain.book.Book;
import com.aluracursos.literalura.domain.book.DataBook;
import com.aluracursos.literalura.service.WebSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller("eapicontroller")
@RequestMapping("/web")
public class ExternalApiController {

    @Autowired
    private WebSearchService wsservice;


    @GetMapping("/{bookname}")
    public ResponseEntity<?> externalSearch(@PathVariable String bookname) {
        System.out.println(bookname);
        Optional<Book> book = wsservice.webSearchAndSave(bookname);
        System.out.println(book);
//        if (book.isPresent()) {
//            return ResponseEntity.of(book).ok().build();
//        }else{
//            return ResponseEntity.notFound().build();
//        }
        return book.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Libro no encontrado") :
                ResponseEntity.ok(book);

    }
}
