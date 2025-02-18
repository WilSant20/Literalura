package com.aluracursos.literalura.service.impl;

import com.aluracursos.literalura.domain.book.DataBookIn;
import com.aluracursos.literalura.service.IExternalApiService;
import com.aluracursos.literalura.utils.IDataConversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ExternalApiService implements IExternalApiService {

    @Autowired
    @Qualifier("dataMapper")
    private IDataConversor iDataConversor;

    public String getData(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
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

    public DataBookIn apiProcessedConsuption(String bookName) {
        String URL = "https://gutendex.com/books/?search=";
        String json = getData(URL + bookName.replace(" ", "%20"));
        return iDataConversor.getData(json, DataBookIn.class);
    }

}
