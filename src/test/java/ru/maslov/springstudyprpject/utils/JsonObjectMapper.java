package ru.maslov.springstudyprpject.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObjectMapper {
    public static String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("error, while mapping object to string", e);
        }
    }
}
