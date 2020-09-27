package org.nalanta.json;

import java.util.Map;

public abstract class AbstractJsonObject implements JsonObject {

    final Map<String, JsonEntity> internal;

    AbstractJsonObject(Map<String, JsonEntity> map) {
        internal = map;
    }

    @Override
    public JsonObject getJsonObject(String key) {
        return (JsonObject) internal.get(key);
    }

    @Override
    public JsonArray getJsonArray(String key) {
        return (JsonArray) internal.get(key);
    }

    @Override
    public JsonString getJsonString(String key) {
        return (JsonString) internal.get(key);
    }

    @Override
    public JsonNumber getJsonNumber(String key) {
        return (JsonNumber) internal.get(key);
    }

    @Override
    public JsonBoolean getJsonBoolean(String key) {
        return (JsonBoolean) internal.get(key);
    }

    @Override
    public Boolean getBoolean(String key) {
        return ((JsonBoolean) internal.get(key)).internalBoolean();
    }

    @Override
    public String getString(String key) {
        return ((JsonString) internal.get(key)).internalString();
    }

    @Override
    public Long getLong(String key) {
        return ((JsonNumber) internal.get(key)).internalLong();
    }

    @Override
    public Integer getInteger(String key) {
        return ((JsonNumber) internal.get(key)).internalInteger();
    }

    @Override
    public Double getDouble(String key) {
        return ((JsonNumber) internal.get(key)).internalDouble();
    }

    @Override
    public Float getFloat(String key) {
        return ((JsonNumber) internal.get(key)).internalFloat();
    }

    @Override
    public JsonObject putJsonObject(String key, JsonObject value) {
        internal.put(key, value);
        return this;
    }

    @Override
    public JsonObject putJsonArray(String key, JsonArray value) {
        internal.put(key, value);
        return this;
    }

    @Override
    public JsonObject putJsonBoolean(String key, JsonBoolean value) {
        internal.put(key, value);
        return this;
    }

    @Override
    public JsonObject putJsonNumber(String key, JsonNumber value) {
        internal.put(key, value);
        return this;
    }

    @Override
    public JsonObject putJsonString(String key, JsonString value) {
        internal.put(key, value);
        return this;
    }

    @Override
    public JsonObject putBoolean(String key, Boolean value) {
        internal.put(key, new JsonBoolean(value));
        return this;
    }

    @Override
    public JsonObject putNumber(String key, Number value) {
        internal.put(key, new JsonNumber(value));
        return this;
    }

    @Override
    public JsonObject putNumber(String key, Integer value) {
        internal.put(key, new JsonNumber(value));
        return this;
    }

    @Override
    public JsonObject putNumber(String key, Long value) {
        internal.put(key, new JsonNumber(value));
        return this;
    }

    @Override
    public JsonObject putNumber(String key, Double value) {
        internal.put(key, new JsonNumber(value));
        return this;
    }

    @Override
    public JsonObject putNumber(String key, Float value) {
        internal.put(key, new JsonNumber(value));
        return this;
    }

    @Override
    public JsonObject putString(String key, String value) {
        internal.put(key, new JsonString(value));
        return this;
    }

}
