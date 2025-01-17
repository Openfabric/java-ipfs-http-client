package io.ipfs.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class JSONParser {

    private static ObjectWriter printer;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, Object> parse(String json) {
        return parse(json, HashMap.class);
    }

    public static <T> T parse(String json, Class<T> clazz) {
        if (json == null || "".equals(json.trim())) {
            return null;
        }

        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static List<?> parseStream(String jsonStream) {
        if ("".equals(jsonStream.trim())) {
            return new ArrayList<>();
        }

        return Arrays.stream(jsonStream.split("\n"))
                .map(e -> parse(e, HashMap.class))
                .collect(Collectors.toList());
    }

    public static String toString(Object obj) {
        try {
            if (printer == null) {
                printer = mapper.writer();
            }
            return printer.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
