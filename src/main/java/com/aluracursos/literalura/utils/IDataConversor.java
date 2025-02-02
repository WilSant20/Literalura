package com.aluracursos.literalura.utils;

public interface IDataConversor {
    <T> T getData(String json, Class<T> clase);
}
