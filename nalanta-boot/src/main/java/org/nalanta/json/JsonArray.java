package org.nalanta.json;

import java.util.RandomAccess;

import static org.nalanta.json.JsonEntity.Type.ARRAY;

public interface JsonArray extends JsonEntity, Iterable<JsonEntity>, RandomAccess {

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

    JsonObject share();

    int size();

    JsonArray add(Object element);

    JsonArray addJsonEntity(JsonEntity element);

    JsonArray addJsonObject(JsonObject element);

    JsonArray addJsonArray(JsonArray element);

    JsonArray addJsonString(JsonString element);

    JsonArray addJsonNumber(JsonNumber element);

}
