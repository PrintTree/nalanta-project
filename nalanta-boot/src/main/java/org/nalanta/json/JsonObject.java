package org.nalanta.json;

import java.util.Map;

public class JsonObject {

    public JsonObject from(String jsonString) {
        return this;
    }

    public JsonObject from(Map<String, Object> jsonMap) {
        return this;
    }

    public String stringify() {
        return null;
    }

    public String string(String key) {
        return null;
    }

    public JsonObject string(String key, String value) {
        return this;
    }

    public Integer integer(String key) {
        return null;
    }

    public JsonObject integer(String key, Integer value) {
        return this;
    }

    public Double number(String key) {
        return null;
    }

    public JsonObject number(String key, Double value) {
        return this;
    }

    public Boolean bool(String key) {
        return null;
    }

    public JsonObject bool(String key, Boolean value) {
        return this;
    }

    public JsonObject object(String key) {
        return null;
    }

    public JsonObject object(String key, JsonObject value) {
        return this;
    }

    public JsonArray array(String key) {
        return null;
    }

    public JsonObject array(String key, JsonObject value) {
        return this;
    }
}
