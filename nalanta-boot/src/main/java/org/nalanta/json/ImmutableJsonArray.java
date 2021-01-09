package org.nalanta.json;

import java.util.List;
import java.util.Map;

class ImmutableJsonArray extends AbstractJsonArray {

    /**
     * cache the string content of this JsonArray, because it's immutable.
     */
    private String stringifyCache;

    private List<Object> simplifyCache;

    ImmutableJsonArray(List<JsonEntity> list) {
        super(list);
        stringifyCache = super.stringify();
    }

    @Override
    public JsonArray freeze() {
        return this;
    }

    @Override
    public JsonArray share() {
        return this;
    }

    @Override
    public boolean isImmutable() {
        return true;
    }

    @Override
    public String stringify() {
        if(stringifyCache == null) {
            synchronized (this) {
                if(stringifyCache == null) {
                    stringifyCache = super.stringify();
                }
            }
        }
        return stringifyCache;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object> simplify() {
        if(simplifyCache == null) {
            synchronized (this) {
                if(simplifyCache == null) {
                    simplifyCache = (List<Object>) super.simplify();
                }
            }
        }
        return simplifyCache;
    }

    @Override
    public JsonArray add(Object element) {
        return this;
    }

    @Override
    public JsonArray add(JsonEntity element) {
        return this;
    }

    @Override
    public JsonArray add(String element) {
        return this;
    }

    @Override
    public JsonArray add(Number element) {
        return this;
    }

    @Override
    public JsonArray add(Boolean element) {
        return this;
    }

    @Override
    public JsonArray set(int index, Object element) {
        return this;
    }

    @Override
    public JsonArray set(int index, JsonEntity element) {
        return this;
    }

    @Override
    public JsonArray set(int index, String element) {
        return this;
    }

    @Override
    public JsonArray set(int index, Number element) {
        return this;
    }

    @Override
    public JsonArray set(int index, Boolean element) {
        return this;
    }

    @Override
    public JsonArray remove(int index) {
        return this;
    }

    @Override
    public JsonEntity take(int index) {
        return getJsonEntity(index);
    }

    @Override
    public JsonArray clear() {
        return this;
    }
}
