package com.aluracursos.literalura.controllers;

import com.aluracursos.literalura.service.DatabaseRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller("bookcontroller")
@RequestMapping("/search")
public class BookController {

    @Autowired
    DatabaseRequestService dbrequestService;


    @GetMapping("/author/{authorName}")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable String authorName) {
        Optional<?> books = dbrequestService.findByAuthor(authorName);
        return books.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Solicitud no encontrada") :
                ResponseEntity.ok(books);
    }


}
