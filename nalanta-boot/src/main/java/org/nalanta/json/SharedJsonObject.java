package org.nalanta.json;

import java.util.Map;

class SharedJsonObject extends AbstractJsonObject {

    SharedJsonObject(Map<String, JsonEntity> map) {
        super(map);
    }

    @Override
    public synchronized JsonObject freeze() {
        return new ImmutableJsonObject(internal);
    }

    @Override
    public synchronized boolean isImmutable() {
        return false;
    }

    @Override
    public synchronized String stringify() {
        return "{}";
    }

    @Override
    public synchronized JsonObject getJsonObject(String key) {
        return super.getJsonObject(key);
    }

    @Override
    public synchronized JsonArray getJsonArray(String key) {
        return super.getJsonArray(key);
    }

    @Override
    public synchronized JsonString getJsonString(String key) {
        return super.getJsonString(key);
    }

    @Override
    public synchronized JsonNumber getJsonNumber(String key) {
        return super.getJsonNumber(key);
    }

    @Override
    public synchronized JsonBoolean getJsonBoolean(String key) {
        return super.getJsonBoolean(key);
    }

    @Override
    public synchronized Boolean getBoolean(String key) {
        return super.getBoolean(key);
    }

    @Override
    public synchronized String getString(String key) {
        return super.getString(key);
    }

    @Override
    public synchronized Long getLong(String key) {
        return super.getLong(key);
    }

    @Override
    public synchronized Integer getInteger(String key) {
        return super.getInteger(key);
    }

    @Override
    public synchronized Double getDouble(String key) {
        return super.getDouble(key);
    }

    @Override
    public synchronized Float getFloat(String key) {
        return super.getFloat(key);
    }
}
