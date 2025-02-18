package com.aluracursos.literalura.domain.author;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.io.Serializable;
import java.util.List;

public class AutorResponseDTO implements Serializable {
//    @JsonAlias("name")
    private final String name;
//    @JsonAlias("birth_year")
    private final Integer birthDate;
//    @JsonAlias("death_year")
    private final Integer deathDate;
    private final List<String> books;

    public AutorResponseDTO(String name, Integer birthDate, Integer deathDate, List<String> books) {
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public Integer getBirthDate() {
        return birthDate;
    }

    public Integer getDeathDate() {
        return deathDate;
    }

    public List<String> getBooks() {
        return books;
    }
}
