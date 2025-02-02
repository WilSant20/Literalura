package com.aluracursos.literalura.domain.author;

import com.aluracursos.literalura.utils.NameUtils;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataAuthor(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Integer birthDate,
        @JsonAlias("death_year") Integer deathDate
) {
    static NameUtils nameUtils = new NameUtils();
    @Override
    public String name() {
        return nameUtils.reorderName(name);
    }
}
