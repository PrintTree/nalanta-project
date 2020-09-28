package org.nalanta.json;

import java.util.Map;

public abstract class AbstractJsonObject implements JsonObject {

    final Map<String, JsonEntity> internal;

    AbstractJsonObject(Map<String, JsonEntity> map) {
        internal = map;
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
                if(Byte.class.isAssignableFrom(numberType) ||
                   Short.class.isAssignableFrom(numberType) ||
                   Integer.class.isAssignableFrom(numberType)) {
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
    public JsonObject putJsonEntity(String key, JsonEntity value) {
        internal.put(key, value);
        return this;
    }

    @Override
    public JsonObject putJsonObject(String key, JsonObject value) {
        internal.put(key, value);
        return this;
    }

    @Override
    public JsonObject putJsonArray(String key, JsonArray value) {
        internal.put(key, value);
        return this;
    }

    @Override
    public JsonObject putJsonBoolean(String key, JsonBoolean value) {
        internal.put(key, value);
        return this;
    }

    @Override
    public JsonObject putJsonNumber(String key, JsonNumber value) {
        internal.put(key, value);
        return this;
    }

    @Override
    public JsonObject putJsonString(String key, JsonString value) {
        internal.put(key, value);
        return this;
    }

    @Override
    public JsonObject putBoolean(String key, Boolean value) {
        internal.put(key, new JsonBoolean(value));
        return this;
    }

    @Override
    public JsonObject putNumber(String key, Number value) {
        internal.put(key, new JsonNumber(value));
        return this;
    }

    @Override
    public JsonObject putInteger(String key, Integer value) {
        internal.put(key, new JsonNumber(value));
        return this;
    }

    @Override
    public JsonObject putLong(String key, Long value) {
        internal.put(key, new JsonNumber(value));
        return this;
    }

    @Override
    public JsonObject putDouble(String key, Double value) {
        internal.put(key, new JsonNumber(value));
        return this;
    }

    @Override
    public JsonObject putFloat(String key, Float value) {
        internal.put(key, new JsonNumber(value));
        return this;
    }

    @Override
    public JsonObject putString(String key, String value) {
        internal.put(key, new JsonString(value));
        return this;
    }

}
