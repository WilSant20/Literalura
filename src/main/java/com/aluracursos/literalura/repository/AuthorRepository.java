package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.domain.author.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("authorrepository")
public interface AuthorRepository extends JpaRepository<Author, Long>{
    Optional<Author> findByName(String name);

    Page<Author> findByNameContainsIgnoreCase(Pageable pageable,
    String name);

    @Query("SELECT a FROM Author a WHERE a.birthDate <= :endDate AND (a.deathDate IS NULL OR a.deathDate >= " +
            ":startDate)")
    Page<Author> findByEpochAlive(Pageable pageable, Integer startDate, Integer endDate);
}