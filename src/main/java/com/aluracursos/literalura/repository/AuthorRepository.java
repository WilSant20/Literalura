package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.domain.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("authorrepository")
public interface AuthorRepository extends JpaRepository<Author, Long>{
    Optional<Author> findByName(String name);
}