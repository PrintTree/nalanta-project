package org.nalanta.json;

public interface JsonObject extends JsonEntity {

    static JsonObject from(String jsonString) {
        return null;
    }

    static JsonObject create() {
        return null;
    }

    JsonObject freeze();

}
