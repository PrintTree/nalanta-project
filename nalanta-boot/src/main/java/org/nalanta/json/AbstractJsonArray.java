package org.nalanta.json;

import java.util.List;

public abstract class AbstractJsonArray implements JsonArray {

    final List<JsonEntity> internal;

    AbstractJsonArray(List<JsonEntity> list) {
        internal = list;
    }

    @Override
    public int size() {
        return internal.size();
    }

    @Override
    public JsonArray add(Object element) {
        return this;
    }

    @Override
    public JsonArray add(JsonEntity element) {
        internal.add(element);
        return this;
    }

    @Override
    public JsonArray add(String element) {
        internal.add(new JsonString(element));
        return this;
    }

    @Override
    public JsonArray add(Number element) {
        internal.add(new JsonNumber(element));
        return this;
    }

    @Override
    public JsonArray add(Boolean element) {
        internal.add(new JsonBoolean(element));
        return this;
    }

    @Override
    public Object get(int index) {
        return this;
    }

    @Override
    public JsonEntity getJsonEntity(int index) {
        return internal.get(index);
    }

    @Override
    public JsonObject getJsonObject(int index) {
        return (JsonObject) internal.get(index);
    }

    @Override
    public JsonArray getJsonArray(int index) {
        return (JsonArray) internal.get(index);
    }

    @Override
    public JsonString getJsonString(int index) {
        return (JsonString) internal.get(index);
    }

    @Override
    public JsonNumber getJsonNumber(int index) {
        return (JsonNumber) internal.get(index);
    }

    @Override
    public JsonBoolean getJsonBoolean(int index) {
        return (JsonBoolean) internal.get(index);
    }

    @Override
    public String getString(int index) {
        return ((JsonString) internal.get(index)).internalString();
    }

    @Override
    public Number getNumber(int index) {
        return ((JsonNumber) internal.get(index)).internalNumber();
    }

    @Override
    public Integer getInteger(int index) {
        return ((JsonNumber) internal.get(index)).internalInteger();
    }

    @Override
    public Long getLong(int index) {
        return ((JsonNumber) internal.get(index)).internalLong();
    }

    @Override
    public Float getFloat(int index) {
        return ((JsonNumber) internal.get(index)).internalFloat();
    }

    @Override
    public Double getDouble(int index) {
        return ((JsonNumber) internal.get(index)).internalDouble();
    }

    @Override
    public Boolean getBoolean(int index) {
        return ((JsonBoolean) internal.get(index)).internalBoolean();
    }
}
