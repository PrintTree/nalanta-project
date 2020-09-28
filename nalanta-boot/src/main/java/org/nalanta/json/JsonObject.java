package org.nalanta.json;

import static org.nalanta.json.JsonEntity.Type.OBJECT;

public interface JsonObject extends JsonEntity {

    static JsonObject from(String jsonString) {
        try {
            return (JsonObject) JsonUtil.objectMapper.readValue(jsonString, JsonEntity.class);
        } catch (Exception e) {
            //TODO config logger
            e.printStackTrace();
            return null;
        }
    }

    static JsonObject create() {
        return new StandardJsonObject();
    }

    @Override
    default Type type() {
        return OBJECT;
    }

    default JsonObject freeze() {
        return JsonObject.create().freeze();
    }

    default JsonObject share() {
        return JsonObject.create().share();
    }

    Object get(String key);

    JsonEntity getJsonEntity(String key);

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

    JsonObject put(String key, Object value);

    JsonObject putJsonEntity(String key, JsonEntity value);

    JsonObject putJsonObject(String key, JsonObject value);

    JsonObject putJsonArray(String key, JsonArray value);

    JsonObject putJsonBoolean(String key, JsonBoolean value);

    JsonObject putJsonNumber(String key, JsonNumber value);

    JsonObject putJsonString(String key, JsonString value);

    JsonObject putBoolean(String key, Boolean value);

    JsonObject putNumber(String key, Number value);

    JsonObject putInteger(String key, Integer value);

    JsonObject putLong(String key, Long value);

    JsonObject putDouble(String key, Double value);

    JsonObject putFloat(String key, Float value);

    JsonObject putString(String key, String value);

}
