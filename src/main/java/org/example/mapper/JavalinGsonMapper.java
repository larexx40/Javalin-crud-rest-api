package org.example.mapper;

import com.google.gson.Gson;
import io.javalin.json.JsonMapper;

import java.lang.reflect.Type;

public class JavalinGsonMapper implements JsonMapper {
    private final Gson gson;

    public JavalinGsonMapper(Gson gson) {
        this.gson = gson;
    }

    // Convert object to JSON string
    public String toJsonString(Object obj) {
        return gson.toJson(obj);
    }

    // Convert JSON string to object of specific class
    public <T> T fromJsonString(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    // Convert JSON string to object of specific type
    public <T> T fromJsonString(String json, Type type) {
        return gson.fromJson(json, type);
    }
}