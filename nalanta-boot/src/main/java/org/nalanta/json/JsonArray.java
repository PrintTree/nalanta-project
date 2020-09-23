package org.nalanta.json;

import static org.nalanta.json.JsonEntity.Type.ARRAY;

public interface JsonArray extends JsonEntity {

    static JsonArray from(String jsonString) {
        return null;
    }

    static JsonArray create() {
        return new StandardJsonArray();
    }

    @Override
    default Type type() {
        return ARRAY;
    }

    JsonObject freeze();

}
