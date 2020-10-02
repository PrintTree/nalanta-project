package org.nalanta.json;

import java.util.List;

class SharedJsonArray extends AbstractJsonArray {

    SharedJsonArray(List<JsonEntity> list) {
        super(list);
    }

    @Override
    public synchronized JsonArray copy() {
        return super.copy();
    }

    @Override
    public synchronized JsonArray freeze() {
        return super.freeze();
    }

    @Override
    public synchronized JsonArray share() {
        return this;
    }

    @Override
    public synchronized boolean isImmutable() {
        return false;
    }

    @Override
    public synchronized String stringify() {
        return super.stringify();
    }

    @Override
    public synchronized int size() {
        return super.size();
    }

    @Override
    public synchronized JsonArray add(Object element) {
        return super.add(element);
    }

    @Override
    public synchronized JsonArray add(JsonEntity element) {
        return super.add(element);
    }

    @Override
    public synchronized JsonArray add(String element) {
        return super.add(element);
    }

    @Override
    public synchronized JsonArray add(Number element) {
        return super.add(element);
    }

    @Override
    public synchronized JsonArray add(Boolean element) {
        return super.add(element);
    }

    @Override
    public synchronized JsonArray set(int index, Object element) {
        return super.set(index, element);
    }

    @Override
    public synchronized JsonArray set(int index, JsonEntity element) {
        return super.set(index, element);
    }

    @Override
    public synchronized JsonArray set(int index, String element) {
        return super.set(index, element);
    }

    @Override
    public synchronized JsonArray set(int index, Number element) {
        return super.set(index, element);
    }

    @Override
    public synchronized JsonArray set(int index, Boolean element) {
        return super.set(index, element);
    }

    @Override
    public synchronized Object get(int index) {
        return super.get(index);
    }

    @Override
    public synchronized JsonEntity getJsonEntity(int index) {
        return super.getJsonEntity(index);
    }

    @Override
    public synchronized JsonObject getJsonObject(int index) {
        return super.getJsonObject(index);
    }

    @Override
    public synchronized JsonArray getJsonArray(int index) {
        return super.getJsonArray(index);
    }

    @Override
    public synchronized JsonString getJsonString(int index) {
        return super.getJsonString(index);
    }

    @Override
    public synchronized JsonNumber getJsonNumber(int index) {
        return super.getJsonNumber(index);
    }

    @Override
    public synchronized JsonBoolean getJsonBoolean(int index) {
        return super.getJsonBoolean(index);
    }

    @Override
    public synchronized String getString(int index) {
        return super.getString(index);
    }

    @Override
    public synchronized Number getNumber(int index) {
        return super.getNumber(index);
    }

    @Override
    public synchronized Integer getInteger(int index) {
        return super.getInteger(index);
    }

    @Override
    public synchronized Long getLong(int index) {
        return super.getLong(index);
    }

    @Override
    public synchronized Float getFloat(int index) {
        return super.getFloat(index);
    }

    @Override
    public synchronized Double getDouble(int index) {
        return super.getDouble(index);
    }

    @Override
    public synchronized Boolean getBoolean(int index) {
        return super.getBoolean(index);
    }

    @Override
    public synchronized JsonArray remove(int index) {
        return super.remove(index);
    }

    @Override
    public synchronized JsonEntity take(int index) {
        return super.take(index);
    }

    @Override
    public synchronized JsonArray clear() {
        return super.clear();
    }

}
