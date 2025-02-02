package com.aluracursos.literalura.domain.book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Languages {
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    PORTUGUESE("pt"),
    ITALIAN("it");

    private String apiLanguages;
    private static Map<String, Languages> languagesMap = new HashMap<>();

    static {
        for (Languages languages : Languages.values()) {
            languagesMap.put(languages.apiLanguages.toLowerCase(), languages);
        }
    }


    Languages(String apiLanguage) {
        this.apiLanguages = apiLanguage;
    }

    public String getApiLanguages() {
        return apiLanguages;
    }

    public static Languages fromString(String text) {

        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("El texto proporcionado es nulo o vacío.");
        }
        Languages language = languagesMap.get(text.toLowerCase());
        if (language == null) {
            throw new IllegalArgumentException("El lenguage no coincide con los lenguajes aceptados");
        }
        return language;
    }

}
