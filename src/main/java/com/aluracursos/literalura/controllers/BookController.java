package com.aluracursos.literalura.controllers;

import com.aluracursos.literalura.domain.book.Book;
import com.aluracursos.literalura.service.IBookDbRequestService;
import com.aluracursos.literalura.service.impl.BookDbRequestServiceImpl;
import com.aluracursos.literalura.utils.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("Book")
@RequestMapping("/search/books")
public class BookController {

    @Autowired
    private IBookDbRequestService dbrequestService;
    @Autowired
    private PagedResourcesAssembler<Book> pagedResourcesAssembler;


    @GetMapping("/author/{authorName}")
    public ResponseEntity<?> getBooksByAuthor(@Parameter(hidden = true) Pageable pageable,
        @RequestParam(defaultValue = "title,asc") String sort,
        @RequestParam String authorName) {
        Sort sorting = Sort.by(sort.split(","));
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting);
        Page<Book> books = dbrequestService.findByAuthor(pageable , authorName);
        PagedModel<EntityModel<Book>> model = pagedResourcesAssembler.toModel(books);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getBooksByName(@Parameter(hidden = true)
            @PageableDefault Pageable pageable, @RequestParam String name) {
        Page<Book> books = dbrequestService.searchBookByName(pageable, name);
        System.out.println(books);
        PagedModel<EntityModel<Book>> model = pagedResourcesAssembler.toModel(books);
        System.out.println(model);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/top10")
    public ResponseEntity<PagedModel<EntityModel<Book>>> getTop10Books( @PageableDefault(size = 10) @ParameterObject Pageable pageable) {
        Page<Book> books = dbrequestService.searchTop10PopularBooks(pageable);
        PagedModel<EntityModel<Book>> model = pagedResourcesAssembler.toModel(books);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/topics/{topic}")
    public ResponseEntity<PagedModel<EntityModel<Book>>> getBooksByTopic(@Parameter(hidden = true) @PageableDefault Pageable pageable,@RequestParam  String topic) {
        Page<Book> books = dbrequestService.searchBooksByTopic(pageable, topic);
        PagedModel<EntityModel<Book>> model = pagedResourcesAssembler.toModel(books);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getBooksById(@RequestParam Long id) {
        Optional<Book> book = dbrequestService.searchBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<PagedModel<EntityModel<Book>>> getBooksByLanguage(@Parameter(hidden = true) @PageableDefault Pageable pageable, @RequestParam String  language) {
        Page<Book> books = dbrequestService.searchBooksByLanguage(pageable, language);
        PagedModel<EntityModel<Book>> model = pagedResourcesAssembler.toModel(books);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/author/years")
    public ResponseEntity<PagedModel<EntityModel<Book>>> getBooksByAuthorTimeRange(@Parameter(hidden = true) @PageableDefault Pageable pageable,  @RequestParam Integer birthdate, @RequestParam Integer deathdate) {
        Page<Book> books = dbrequestService.findAuthorInRange(pageable, birthdate, deathdate);
        PagedModel<EntityModel<Book>> model = pagedResourcesAssembler.toModel(books);
        return  ResponseEntity.ok(model);
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<Book>> getAllBooks(@Parameter(hidden = true) @PageableDefault Pageable pageable, @RequestParam(defaultValue = "title,asc") String sort) {
        Sort sorting = Sort.by(sort.split(","));
        Pageable customPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting);
        Page<Book> books = dbrequestService.listAllBooks(pageable);
        return ResponseEntity.ok(new PageResponse<>(books)) ;
    }

}
