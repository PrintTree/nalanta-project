package org.nalanta.json;

import java.util.Map;

import static org.nalanta.json.JsonEntity.Type.OBJECT;

public interface JsonObject extends JsonEntity {

    static JsonObject from(String jsonString) {
        return JsonUtil.parseObject(jsonString);
    }

    static JsonObject from(Map<String, ?> map) {
        JsonObject parsed = null;
        try {
            parsed = JsonUtil.parseObject(map);
        } catch (Exception ignored) {
            //warn log
        }
        return parsed == null ? create() : parsed;
    }

    static JsonObject from(Object in) {
        if (in instanceof String) {
            return from((String) in);
        } else if (in instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, ?> map = (Map<String, ?>) in;
            return from(map);
        } else {
            //warn log
            return create();
        }
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
