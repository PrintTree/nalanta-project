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
        if(sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        stringifyCache = sb.append('}').toString();
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

}
