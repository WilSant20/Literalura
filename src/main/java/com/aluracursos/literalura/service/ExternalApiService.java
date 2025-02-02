package com.aluracursos.literalura.service;

import com.aluracursos.literalura.domain.book.DataBook;
import com.aluracursos.literalura.utils.IDataConversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service("apiuse")
public class ExternalApiService {

    @Autowired
    @Qualifier("dataMapper")
    private IDataConversor iDataConversor;
    private final String URL = "https://gutendex.com/books/?search=";

    public String getData(String URL){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .build();
        HttpResponse<String> response;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
         }
        return response.body();
    }

    public DataBook apiProcessedConsuption(String bookName) {
        String json = getData(URL + bookName.replace(" ", "%20"));
        return iDataConversor.getData(json, DataBook.class);
    }

}
