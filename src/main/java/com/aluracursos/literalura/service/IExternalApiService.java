package com.aluracursos.literalura.service;

import com.aluracursos.literalura.domain.book.DataBookIn;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface IExternalApiService {
    String getData(String url);

    DataBookIn apiProcessedConsuption(String bookName);
}
