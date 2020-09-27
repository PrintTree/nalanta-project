package org.nalanta.json;

import static org.nalanta.json.JsonEntity.Type.OBJECT;

public interface JsonObject extends JsonEntity {

    static JsonObject from(String jsonString) {
        return null;
    }

    static JsonObject create() {
        return new StandardJsonObject();
    }

    @Override
    default Type type() {
        return OBJECT;
    }

    JsonObject freeze();

    default JsonObject share() {
        return null;
    }

    JsonObject getJsonObject(String key);

    JsonArray getJsonArray(String key);

    JsonString getJsonString(String key);

    JsonNumber getJsonNumber(String key);

    JsonBoolean getJsonBoolean(String key);

    Boolean getBoolean(String key);

    String getString(String key);

    Long getLong(String key);

    Integer getInteger(String key);

    Double getDouble(String key);

    Float getFloat(String key);

    JsonObject putJsonObject(String key, JsonObject value);

    JsonObject putJsonArray(String key, JsonArray value);

    JsonObject putJsonBoolean(String key, JsonBoolean value);

    JsonObject putJsonNumber(String key, JsonNumber value);

    JsonObject putJsonString(String key, JsonString value);

    JsonObject putBoolean(String key, Boolean value);

    JsonObject putNumber(String key, Number value);

    JsonObject putNumber(String key, Integer value);

    JsonObject putNumber(String key, Long value);

    JsonObject putNumber(String key, Double value);

    JsonObject putNumber(String key, Float value);

    JsonObject putString(String key, String value);

}
