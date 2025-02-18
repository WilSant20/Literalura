package com.aluracursos.literalura.domain.book;

import com.aluracursos.literalura.domain.author.DataAuthor;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.util.List;

public class BookDTO implements Serializable {
    @JsonAlias("title") final String title;
    @JsonAlias("summaries") final String summary;
    @JsonAlias("authors") final
    List<DataAuthor> authors;
    @JsonAlias("subjects") final List<String> subjects;
    @JsonAlias("languages") final List<String> languages;
    @JsonAlias("copyright") final Boolean copyright;
    @JsonAlias("download_count") final Integer downloadCount;


    public BookDTO(String title, String summary,
       List<DataAuthor> authors, List<String> subjects,
                   List<String> languages, Boolean copyright, Integer downloadCount) {
        this.title = title;
        this.summary = summary;
        this.authors = authors;
        this.subjects = subjects;
        this.languages = languages;
        this.copyright = copyright;
        this.downloadCount = downloadCount;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public List<DataAuthor> getAuthors() {
        return authors;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public Boolean getCopyright() {
        return copyright;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

//    @JsonSetter("summaries")
//    public void setSummary(List<String> summaries) {
//        this.summary = String.join(" ", summaries);
//    }
}
