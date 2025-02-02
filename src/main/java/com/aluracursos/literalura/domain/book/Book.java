package com.aluracursos.literalura.domain.book;

import com.aluracursos.literalura.domain.author.Author;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "Book")
@Table(name = "books")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id"))
    @JsonManagedReference
    private List<Author> authors;
    @ElementCollection
    private List<String> subjects;
    @Enumerated(EnumType.STRING)
    private Languages languages;
    private Boolean copyright;
    private Integer downloadCount;

    public Book() {}


    public Book(DataBook dataBook) {
        this.title = dataBook.title();
        this.authors = dataBook.authors().stream().map(Author::new).collect(Collectors.toList());
        this.subjects = dataBook.subjects();
        this.languages = Languages.fromString(dataBook.languages().stream().limit(1).collect(Collectors.joining()));
        this.copyright = dataBook.copyright();
        this.downloadCount = dataBook.downloadCount();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public Languages getLanguages() {
        return languages;
    }

    public void setLanguages(Languages languages) {
        this.languages = languages;
    }

    public boolean isCopyright() {
        return copyright;
    }

    public void setCopyright(boolean copyright) {
        this.copyright = copyright;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return "\n-----------------------Book----------------------\n"+
                "\nID: " + id + "\n" +
                "Titulo: " + title + "\n" +
                "Autor: " + authors + "\n" +
                "Géneros: " + subjects + "\n" +
                "Lenguaje: " + languages + "\n" +
                "Copyright: " + copyright + "\n" +
                "Descargas: " + downloadCount + "\n" +
                "\n------------------------------------------------";
    }
}

