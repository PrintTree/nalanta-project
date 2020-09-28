package org.nalanta.json;

import static org.nalanta.json.JsonEntity.Type.ARRAY;

public interface JsonArray extends JsonEntity, Iterable<JsonEntity> {

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

    JsonArray addJsonBoolean(JsonBoolean element);

    JsonArray addString(String element);

    JsonArray addNumber(Number element);

    JsonArray addInteger(Integer element);

    JsonArray addLong(Long element);

    JsonArray addFloat(Float element);

    JsonArray addDouble(Double element);

    JsonArray addBoolean(Boolean element);

    Object get(int index);

    JsonEntity getJsonEntity(int index);

    JsonObject getJsonObject(int index);

    JsonArray getJsonArray(int index);

    JsonString getJsonString(int index);

    JsonNumber getJsonNumber(int index);

    JsonBoolean getJsonBoolean(int index);

    String getString(int index);

    Number getNumber(int index);

    Integer getInteger(int index);

    Long getLong(int index);

    Float getFloat(int index);

    Double getDouble(int index);

    Boolean getBoolean(int index);

}
