package com.basejava.webapp.util;

import com.basejava.webapp.model.Section;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;

public class JsonParser {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new JsonLocalDateAdapter())
            .registerTypeAdapter(Section.class, new JsonSectionAdapter<>())
            .create();


    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> T read(String text, Class<T> clazz) {
        return GSON.fromJson(text, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

    public static <T> String write(T object, Class<T> clazz) {
        return GSON.toJson(object, clazz);
    }
}
