package org.nalanta.core.json;

import java.util.Map;

public class JsonObject {

    JsonObject from(String jsonString) {
        return this;
    }

    JsonObject from(Map<String, Object> jsonMap) {
        return this;
    }

    String stringify() {
        return null;
    }

    String string(String key) {
        return null;
    }

    JsonObject string(String key, String value) {
        return this;
    }

    Integer integer(String key) {
        return null;
    }

    JsonObject integer(String key, Integer value) {
        return this;
    }

    Double number(String key) {
        return null;
    }

    JsonObject number(String key, Double value) {
        return this;
    }

    JsonObject object(String key) {
        return null;
    }

    JsonObject object(String key, JsonObject value) {
        return this;
    }

    JsonArray array(String key) {
        return null;
    }

    JsonObject array(String key, JsonObject value) {
        return this;
    }
}
