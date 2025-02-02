package com.aluracursos.literalura.domain.author;

import com.aluracursos.literalura.domain.book.Book;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer birthDate;
    private Integer deathDate;
//    @JsonIgnore
    @JsonBackReference
    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    public Author() {}

    public Author(
            DataAuthor dataAuthor) {
        this.name = dataAuthor.name();
        this.birthDate = dataAuthor.birthDate();
        this.deathDate = dataAuthor.deathDate();
    }

    public Author(String name, Integer birthDate, Integer deathDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Integer birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Integer deathDate) {
        this.deathDate = deathDate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Nombre: " + name + ", Nacimiento: " + birthDate + ", Fallecimiento: " + deathDate;
    }
}
