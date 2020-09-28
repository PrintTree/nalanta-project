package org.nalanta.json;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

class SharedJsonObject extends AbstractJsonObject {

    SharedJsonObject(Map<String, JsonEntity> map) {
        super(map);
    }

    @Override
    public synchronized JsonObject freeze() {
        LinkedHashMap<String, JsonEntity> copiedMap = new LinkedHashMap<>(8);
        Set<Map.Entry<String, JsonEntity>> entrySet = internal.entrySet();
        for(Map.Entry<String, JsonEntity> entry : entrySet) {
            String key = entry.getKey();
            JsonEntity value = entry.getValue();
            if(value instanceof JsonObject) {
                copiedMap.put(key, ((JsonObject) value).freeze());
            }
            else if(value instanceof JsonArray) {
                copiedMap.put(key, ((JsonArray) value).freeze());
            }
            else {
                copiedMap.put(key, value);
            }
        }
        return new ImmutableJsonObject(copiedMap);
    }

    @Override
    public synchronized JsonObject share() {
        return this;
    }

    @Override
    public synchronized boolean isImmutable() {
        return false;
    }

    @Override
    public synchronized String stringify() {
        StringBuilder sb = new StringBuilder()
                .append('{');
        Set<Map.Entry<String, JsonEntity>> entrySet = internal.entrySet();
        for(Map.Entry<String, JsonEntity> entry : entrySet) {
            String key = entry.getKey();
            JsonEntity value = entry.getValue();
            sb.append('"').append(key).append('"').append(':').append(value.stringify()).append(',');
        }
        return sb.deleteCharAt(sb.length() - 1).append('}').toString();
    }

    @Override
    public synchronized Object get(String key) {
        return super.get(key);
    }

    @Override
    public synchronized JsonEntity getJsonEntity(String key) {
        return super.getJsonEntity(key);
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

    @Override
    public synchronized JsonObject put(String key, Object value) {
        return super.put(key, value);
    }

    @Override
    public synchronized JsonObject putJsonEntity(String key, JsonEntity value) {
        return super.putJsonEntity(key, value);
    }

    @Override
    public synchronized JsonObject putJsonObject(String key, JsonObject value) {
        return super.putJsonObject(key, value);
    }

    @Override
    public synchronized JsonObject putJsonArray(String key, JsonArray value) {
        return super.putJsonArray(key, value);
    }

    @Override
    public synchronized JsonObject putJsonBoolean(String key, JsonBoolean value) {
        return super.putJsonBoolean(key, value);
    }

    @Override
    public synchronized JsonObject putJsonNumber(String key, JsonNumber value) {
        return super.putJsonNumber(key, value);
    }

    @Override
    public synchronized JsonObject putJsonString(String key, JsonString value) {
        return super.putJsonString(key, value);
    }

    @Override
    public synchronized JsonObject putBoolean(String key, Boolean value) {
        return super.putBoolean(key, value);
    }

    @Override
    public synchronized JsonObject putNumber(String key, Number value) {
        return super.putNumber(key, value);
    }

    @Override
    public synchronized JsonObject putInteger(String key, Integer value) {
        return super.putInteger(key, value);
    }

    @Override
    public synchronized JsonObject putLong(String key, Long value) {
        return super.putLong(key, value);
    }

    @Override
    public synchronized JsonObject putDouble(String key, Double value) {
        return super.putDouble(key, value);
    }

    @Override
    public synchronized JsonObject putFloat(String key, Float value) {
        return super.putFloat(key, value);
    }

    @Override
    public synchronized JsonObject putString(String key, String value) {
        return super.putString(key, value);
    }
}
