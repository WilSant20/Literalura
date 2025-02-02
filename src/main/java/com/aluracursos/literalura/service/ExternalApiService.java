package com.aluracursos.literalura.service.features;

import com.aluracursos.literalura.domain.book.DataBook;
import com.aluracursos.literalura.utils.DataMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service("apiUse")
public class ApiUse {
    private final String URL = "";
    @Qualifier("dataMapper")
    private DataMapper dataMapper;

    public String getData(String URL){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .build();
        HttpResponse<String> response = null;
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
        DataBook data = dataMapper.getData(json, DataBook.class);
        return data;
    }

}
