package com.aluracursos.literalura.service;

public class NameUtils {
    public String reorderName(String name) {
        if (name == null || name.isBlank()) {
            return name;
        }
        String[] parts = name.split(", ");
        if (parts.length == 2) {
            return parts[1].trim() + " " + parts[0].trim();
        } else {
            return name;
        }
    }
}

