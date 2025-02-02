package com.aluracursos.literalura.repository;

public interface AuthorRepository extends org.springframework.data.jpa.repository.JpaRepository<com.aluracursos.literalura.domain.author.Author, java.lang.Long> ,org.springframework.data.jpa.repository.JpaSpecificationExecutor<com.aluracursos.literalura.domain.author.Author> {
  }