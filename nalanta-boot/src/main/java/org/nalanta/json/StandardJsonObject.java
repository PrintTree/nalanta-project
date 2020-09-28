package org.nalanta.json;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

class StandardJsonObject extends AbstractJsonObject {

    StandardJsonObject() {
        super(new LinkedHashMap<>(8));
    }

    @Override
    public JsonObject freeze() {
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
    public JsonObject share() {
        LinkedHashMap<String, JsonEntity> copiedMap = new LinkedHashMap<>(8);
        Set<Map.Entry<String, JsonEntity>> entrySet = internal.entrySet();
        for(Map.Entry<String, JsonEntity> entry : entrySet) {
            String key = entry.getKey();
            JsonEntity value = entry.getValue();
            if(value instanceof JsonObject) {
                copiedMap.put(key, ((JsonObject) value).share());
            }
            else if(value instanceof JsonArray) {
                copiedMap.put(key, ((JsonArray) value).share());
            }
            else {
                copiedMap.put(key, value);
            }
        }
        return new SharedJsonObject(copiedMap);
    }

    @Override
    public boolean isImmutable() {
        return false;
    }

    @Override
    public String stringify() {
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

}
