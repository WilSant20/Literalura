package com.aluracursos.literalura.service;

import com.aluracursos.literalura.domain.author.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAuthorDbRequestService {
    Page<Author> findByName(Pageable pageable, String Name);

    Page<Author> findByEpoch(Pageable pageable, Integer birthDate, Integer deathDate);
}
