package com.aluracursos.literalura.service.impl;

import com.aluracursos.literalura.domain.author.Author;
import com.aluracursos.literalura.repository.AuthorRepository;
import com.aluracursos.literalura.service.IAuthorDbRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorDbRequestServiceImpl  implements IAuthorDbRequestService {
    @Autowired
    private AuthorRepository authorRepository;

    public Page<Author> findByName(Pageable pageable, String Name) {
        return authorRepository.findByNameContainsIgnoreCase(pageable, Name);
    }

    public Page<Author> findByEpoch(Pageable pageable, Integer birthDate, Integer deathDate) {
        return authorRepository.findByEpochAlive(pageable, birthDate, deathDate);
    }


}
