package org.nalanta.json;

import java.util.Map;

class ImmutableJsonObject extends AbstractJsonObject {

    /**
     * cache the string content of this JsonObject, because it's immutable.
     */
    private String stringifyCache;

    ImmutableJsonObject(Map<String, JsonEntity> map) {
        super(map);
        //TODO create stringifyCache
    }

    @Override
    public JsonObject freeze() {
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

}
