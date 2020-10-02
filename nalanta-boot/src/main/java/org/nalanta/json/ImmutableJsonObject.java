package org.nalanta.json;

import java.util.Map;

class ImmutableJsonObject extends AbstractJsonObject {

    /**
     * cache the string content of this JsonObject, because it's immutable.
     */
    private String stringifyCache;

    ImmutableJsonObject(Map<String, JsonEntity> map) {
        super(map);
        stringifyCache = super.stringify();
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
    public JsonObject put(String key, JsonEntity value) {
        return this;
    }

    @Override
    public JsonObject put(String key, Boolean value) {
        return this;
    }

    @Override
    public JsonObject put(String key, Number value) {
        return this;
    }

    @Override
    public JsonObject put(String key, String value) {
        return this;
    }

    @Override
    public JsonObject remove(String key) {
        return this;
    }

    @Override
    public JsonEntity take(String key) {
        return getJsonEntity(key);
    }

    @Override
    public JsonObject clear() {
        return this;
    }

}
