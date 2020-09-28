package org.nalanta.json;

import java.util.Map;
import java.util.Set;

class ImmutableJsonObject extends AbstractJsonObject {

    /**
     * cache the string content of this JsonObject, because it's immutable.
     */
    private String stringifyCache;

    ImmutableJsonObject(Map<String, JsonEntity> map) {
        super(map);
        StringBuilder sb = new StringBuilder()
                .append('{');
        Set<Map.Entry<String, JsonEntity>> entrySet = internal.entrySet();
        for(Map.Entry<String, JsonEntity> entry : entrySet) {
            String key = entry.getKey();
            JsonEntity value = entry.getValue();
            sb.append('"').append(key).append('"').append(':').append(value.stringify()).append(',');
        }
        stringifyCache = sb.deleteCharAt(sb.length() - 1).append('}').toString();
    }

    @Override
    public JsonObject freeze() {
        return this;
    }

    public JsonObject share() {
        return this;
    }

    @Override
    public boolean isImmutable() {
        return true;
    }

    @Override
    public String stringify() {
        return stringifyCache;
    }

    @Override
    public JsonObject put(String key, Object value) {
        return this;
    }

    @Override
    public JsonObject putJsonEntity(String key, JsonEntity value) {
        return this;
    }

    @Override
    public JsonObject putJsonObject(String key, JsonObject value) {
        return this;
    }

    @Override
    public JsonObject putJsonArray(String key, JsonArray value) {
        return this;
    }

    @Override
    public JsonObject putJsonBoolean(String key, JsonBoolean value) {
        return this;
    }

    @Override
    public JsonObject putJsonNumber(String key, JsonNumber value) {
        return this;
    }

    @Override
    public JsonObject putJsonString(String key, JsonString value) {
        return this;
    }

    @Override
    public JsonObject putBoolean(String key, Boolean value) {
        return this;
    }

    @Override
    public JsonObject putNumber(String key, Number value) {
        return this;
    }

    @Override
    public JsonObject putInteger(String key, Integer value) {
        return this;
    }

    @Override
    public JsonObject putLong(String key, Long value) {
        return this;
    }

    @Override
    public JsonObject putDouble(String key, Double value) {
        return this;
    }

    @Override
    public JsonObject putFloat(String key, Float value) {
        return this;
    }

    @Override
    public JsonObject putString(String key, String value) {
        return this;
    }

}
