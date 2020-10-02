package org.nalanta.json;

import static org.nalanta.json.JsonEntity.Type.OBJECT;

public interface JsonObject extends JsonEntity {

    static JsonObject from(String jsonString) {
        return JsonUtil.parseObject(jsonString);
    }

    static JsonObject create() {
        return new StandardJsonObject();
    }

    @Override
    default Type type() {
        return OBJECT;
    }

    JsonObject copy();

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

    JsonObject put(String key, JsonEntity value);

    JsonObject put(String key, Boolean value);

    JsonObject put(String key, Number value);

    JsonObject put(String key, String value);

    JsonObject remove(String key);

    JsonEntity take(String key);

    JsonObject clear();

    int size();

}
