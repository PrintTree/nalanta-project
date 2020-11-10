package org.nalanta.json;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractJsonObject implements JsonObject {

    final Map<String, JsonEntity> internal;

    AbstractJsonObject(Map<String, JsonEntity> map) {
        internal = map;
    }

    @Override
    public JsonObject copy() {
        StandardJsonObject newJsonObject = new StandardJsonObject();
        Set<Map.Entry<String, JsonEntity>> entrySet = internal.entrySet();
        for(Map.Entry<String, JsonEntity> entry : entrySet) {
            String key = entry.getKey();
            JsonEntity value = entry.getValue();
            if(value instanceof JsonObject) {
                newJsonObject.put(key, ((JsonObject) value).copy());
            }
            else if(value instanceof JsonArray) {
                newJsonObject.put(key, ((JsonArray) value).copy());
            }
            else {
                newJsonObject.put(key, value);
            }
        }
        return newJsonObject;
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
    public String stringify() {
        StringBuilder sb = new StringBuilder().append('{');
        Set<Map.Entry<String, JsonEntity>> entrySet = internal.entrySet();
        for(Map.Entry<String, JsonEntity> entry : entrySet) {
            String key = entry.getKey();
            JsonEntity value = entry.getValue();
            sb.append('"').append(key).append('"').append(':').append(value.stringify()).append(',');
        }
        if(sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.append('}').toString();
    }

    @Override
    public Object simplify() {
        Map<String, Object> map = new LinkedHashMap<>();
        for(Map.Entry<String, JsonEntity> e : internal.entrySet()) {
            map.put(e.getKey(), e.getValue().simplify());
        }
        return map;
    }

    @Override
    public Object get(String key) {
        JsonEntity value = internal.get(key);
        switch (value.type()) {
            case OBJECT:
            case ARRAY:
                return value;
            case BOOLEAN:
                return ((JsonBoolean) value).internalBoolean();
            case STRING:
                return ((JsonString) value).internalString();
            case Number:
                JsonNumber jsonNumber = (JsonNumber) value;
                Class<? extends Number> numberType = jsonNumber.getNumberType();
                if(Integer.class.isAssignableFrom(numberType)  ||
                   Short.class.isAssignableFrom(numberType) ||
                   Byte.class.isAssignableFrom(numberType)) {
                    return jsonNumber.internalInteger();
                }
                if(Long.class.isAssignableFrom(numberType)) {
                    return jsonNumber.internalLong();
                }
                if(Float.class.isAssignableFrom(numberType)) {
                    return jsonNumber.internalFloat();
                }
                if(Double.class.isAssignableFrom(numberType)) {
                    return jsonNumber.internalDouble();
                }
                //TODO other supported types
                return Double.NaN;
            default:
                return null;
        }
    }

    @Override
    public JsonEntity getJsonEntity(String key) {
        return internal.get(key);
    }

    @Override
    public JsonObject getJsonObject(String key) {
        return (JsonObject) internal.get(key);
    }

    @Override
    public JsonArray getJsonArray(String key) {
        return (JsonArray) internal.get(key);
    }

    @Override
    public JsonString getJsonString(String key) {
        return (JsonString) internal.get(key);
    }

    @Override
    public JsonNumber getJsonNumber(String key) {
        return (JsonNumber) internal.get(key);
    }

    @Override
    public JsonBoolean getJsonBoolean(String key) {
        return (JsonBoolean) internal.get(key);
    }

    @Override
    public Boolean getBoolean(String key) {
        return ((JsonBoolean) internal.get(key)).internalBoolean();
    }

    @Override
    public String getString(String key) {
        return ((JsonString) internal.get(key)).internalString();
    }

    @Override
    public Long getLong(String key) {
        return ((JsonNumber) internal.get(key)).internalLong();
    }

    @Override
    public Integer getInteger(String key) {
        return ((JsonNumber) internal.get(key)).internalInteger();
    }

    @Override
    public Double getDouble(String key) {
        return ((JsonNumber) internal.get(key)).internalDouble();
    }

    @Override
    public Float getFloat(String key) {
        return ((JsonNumber) internal.get(key)).internalFloat();
    }

    @Override
    public JsonObject put(String key, Object value) {
        if(value instanceof JsonEntity) {
            internal.put(key, (JsonEntity) value);
        }
        else if(value instanceof Number) {
            internal.put(key, new JsonNumber((Number) value));
        }
        else if(value instanceof String) {
            internal.put(key, new JsonString((String) value));
        }
        else if(value instanceof Boolean) {
            internal.put(key, new JsonBoolean((Boolean) value));
        }
        else if(value == null) {
            internal.put(key, new JsonNull());
        }
        //TODO else parse POJO/Map as JsonObject, array/List as JsonArray
        return this;
    }

    @Override
    public JsonObject put(String key, JsonEntity value) {
        internal.put(key, value);
        return this;
    }

    @Override
    public JsonObject put(String key, Boolean value) {
        internal.put(key, new JsonBoolean(value));
        return this;
    }

    @Override
    public JsonObject put(String key, Number value) {
        internal.put(key, new JsonNumber(value));
        return this;
    }

    @Override
    public JsonObject put(String key, String value) {
        internal.put(key, new JsonString(value));
        return this;
    }

    @Override
    public JsonObject remove(String key) {
        internal.remove(key);
        return this;
    }

    @Override
    public JsonEntity take(String key) {
        return internal.remove(key);
    }

    @Override
    public JsonObject clear() {
        internal.clear();
        return this;
    }

    @Override
    public int size() {
        return internal.size();
    }

}
