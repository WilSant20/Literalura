package com.aluracursos.literalura.domain.book;

import java.util.List;

public enum Languages {
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    PORTUGUESE("pt"),
    ITALIAN("it");

    private String apiLanguages;

    Languages(String apiLanguage) {
        this.apiLanguages = apiLanguage;
    }

    public String getApiLanguages() {
        return apiLanguages;
    }

    public static Languages fromString(String text) {
        for (Languages languages : Languages.values()) {
            if (languages.apiLanguages.equalsIgnoreCase(text.toString())) {
                return languages;
            }
        }
        throw new IllegalArgumentException("Ningún código de lenguaje conicide con: "+ text);
    }

}
