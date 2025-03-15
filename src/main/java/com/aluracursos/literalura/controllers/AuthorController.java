package com.aluracursos.literalura.controllers;

import com.aluracursos.literalura.domain.author.Author;
import com.aluracursos.literalura.service.IAuthorDbRequestService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("Authors")
@RequestMapping("/search/authors")
public class AuthorController {
    private final IAuthorDbRequestService adbService;
    private final PagedResourcesAssembler<Author> pagedResourcesAssembler;

    @Autowired
    public AuthorController(IAuthorDbRequestService adbService, PagedResourcesAssembler<Author> pagedResourcesAssembler) {
        this.adbService = adbService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("/name")
    public ResponseEntity<PagedModel<EntityModel<Author>>> findByName(@Parameter(hidden = true) @PageableDefault Pageable pageable, @RequestParam String name) {
        Page<Author> authors = adbService.findByName(pageable, name);
        PagedModel<EntityModel<Author>> model = pagedResourcesAssembler.toModel(authors);
        return ResponseEntity.ok(model);
    }

    @GetMapping("/during")
    public ResponseEntity<PagedModel<EntityModel<Author>>> findByTimeframe(@Parameter(hidden = true) @PageableDefault Pageable pageable, @RequestParam Integer birthDate, Integer deathDate) {
        Page<Author> authors = adbService.findByEpoch(pageable, birthDate, deathDate);
        PagedModel<EntityModel<Author>> model = pagedResourcesAssembler.toModel(authors);
        return ResponseEntity.ok(model);
    }


}
