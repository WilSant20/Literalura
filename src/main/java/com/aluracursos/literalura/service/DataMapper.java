package com.aluracursos.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataMapper implements IDataConversor{
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> tClass){
        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode results = root.path("results");
            if (results.isArray() && results.size() > 0) {
                JsonNode firstResult = results.get(0);
                Class<T> data = objectMapper.treeToValue(firstResult, Class.class);
                System.out.println(data);
                return (T) data;
            } else {
                System.out.println("No se encontraron resultados.");
                return null;
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //    @Override
//    public <T> T getData(String json, Class<T> clase) {
//        try {
//            return objectMapper.readValue(json, clase);
//        } catch (JsonMappingException e) {
//            throw new RuntimeException(e);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
