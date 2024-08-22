package com.aluracursos.literalura.domain.author;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataAuthor(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Integer birthDate,
        @JsonAlias("death_year") Integer deathDate
) {
    @Override
    public String name() {
        return reorderName(name);
    }
    public String reorderName(String name) {
        String[] parts = name.split(", ");
        if (parts.length == 2) {
            return parts[1] + " " + parts[0];
        } else {
            return name;
        }
    }
}
