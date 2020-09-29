package org.nalanta.json;

import java.util.Iterator;
import java.util.List;

class ImmutableJsonArray extends AbstractJsonArray {

    /**
     * cache the string content of this JsonArray, because it's immutable.
     */
    private String stringifyCache;

    ImmutableJsonArray(List<JsonEntity> list) {
        super(list);
        StringBuilder sb = new StringBuilder().append('[');
        for(JsonEntity jsonEntity : internal) {
            sb.append(jsonEntity.stringify()).append(',');
        }
        stringifyCache = sb.deleteCharAt(sb.length() - 1).append(']').toString();
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
        return false;
    }

    @Override
    public String stringify() {
        return stringifyCache;
    }

    @Override
    public Iterator<JsonEntity> iterator() {
        return null;
    }
}
