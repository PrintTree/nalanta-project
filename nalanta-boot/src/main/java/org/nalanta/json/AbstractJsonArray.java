package org.nalanta.json;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractJsonArray implements JsonArray {

    final List<JsonEntity> internal;

    AbstractJsonArray(List<JsonEntity> list) {
        internal = list;
    }

    @Override
    public JsonArray copy() {
        JsonArray newJsonArray = new StandardJsonArray();
        for(JsonEntity jsonEntity : internal) {
            if(jsonEntity instanceof JsonObject) {
                newJsonArray.add(((JsonObject) jsonEntity).copy());
            }
            else if(jsonEntity instanceof JsonArray) {
                newJsonArray.add(((JsonArray) jsonEntity).copy());
            }
            else {
                newJsonArray.add(jsonEntity);
            }
        }
        return newJsonArray;
    }

    @Override
    public JsonArray freeze() {
        ArrayList<JsonEntity> copiedList = new ArrayList<>(internal.size());
        for(JsonEntity jsonEntity : internal) {
            if(jsonEntity instanceof JsonObject) {
                copiedList.add(((JsonObject) jsonEntity).freeze());
            }
            else if(jsonEntity instanceof JsonArray) {
                copiedList.add(((JsonArray) jsonEntity).freeze());
            }
            else {
                copiedList.add(jsonEntity);
            }
        }
        return new ImmutableJsonArray(copiedList);
    }

    @Override
    public JsonArray share() {
        ArrayList<JsonEntity> copiedList = new ArrayList<>(internal.size());
        for(JsonEntity jsonEntity : internal) {
            if(jsonEntity instanceof JsonObject) {
                copiedList.add(((JsonObject) jsonEntity).share());
            }
            else if(jsonEntity instanceof JsonArray) {
                copiedList.add(((JsonArray) jsonEntity).share());
            }
            else {
                copiedList.add(jsonEntity);
            }
        }
        return new SharedJsonArray(copiedList);
    }

    @Override
    public String stringify() {
        StringBuilder sb = new StringBuilder().append('[');
        for(JsonEntity jsonEntity : internal) {
            sb.append(jsonEntity.stringify()).append(',');
        }
        if(sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.append(']').toString();
    }

    @Override
    public Object simplify() {
        List<Object> list = new ArrayList<>(internal.size());
        for(JsonEntity e : internal) {
            list.add(e.simplify());
        }
        return list;
    }

    @Override
    public int size() {
        return internal.size();
    }

    @Override
    public JsonArray add(Object element) {
        if(element instanceof JsonEntity) {
            internal.add((JsonEntity) element);
        }
        else if(element instanceof Number) {
            internal.add(new JsonNumber((Number) element));
        }
        else if(element instanceof String) {
            internal.add(new JsonString((String) element));
        }
        else if(element instanceof Boolean) {
            internal.add(new JsonBoolean((Boolean) element));
        }
        else if(element == null) {
            internal.add(new JsonNull());
        }
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
    public JsonArray set(int index, Object element) {
        if(element instanceof JsonEntity) {
            internal.set(index, (JsonEntity) element);
        }
        else if(element instanceof Number) {
            internal.add(index, new JsonNumber((Number) element));
        }
        else if(element instanceof String) {
            internal.add(index, new JsonString((String) element));
        }
        else if(element instanceof Boolean) {
            internal.add(index, new JsonBoolean((Boolean) element));
        }
        else if(element == null) {
            internal.add(index, new JsonNull());
        }
        return this;
    }

    @Override
    public JsonArray set(int index, JsonEntity element) {
        internal.set(index, element);
        return this;
    }

    @Override
    public JsonArray set(int index, String element) {
        internal.set(index, new JsonString(element));
        return this;
    }

    @Override
    public JsonArray set(int index, Number element) {
        internal.set(index, new JsonNumber(element));
        return this;
    }

    @Override
    public JsonArray set(int index, Boolean element) {
        internal.set(index, new JsonBoolean(element));
        return this;
    }

    @Override
    public Object get(int index) {
        JsonEntity value = internal.get(index);
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
                if (Integer.class.isAssignableFrom(numberType) ||
                        Short.class.isAssignableFrom(numberType) ||
                        Byte.class.isAssignableFrom(numberType)) {
                    return jsonNumber.internalInteger();
                }
                if (Long.class.isAssignableFrom(numberType)) {
                    return jsonNumber.internalLong();
                }
                if (Float.class.isAssignableFrom(numberType)) {
                    return jsonNumber.internalFloat();
                }
                if (Double.class.isAssignableFrom(numberType)) {
                    return jsonNumber.internalDouble();
                }
                //TODO other supported types
                return Double.NaN;
            default:
                return null;
        }
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

    @Override
    public JsonArray remove(int index) {
        internal.remove(index);
        return this;
    }

    @Override
    public JsonEntity take(int index) {
        return internal.remove(index);
    }

    @Override
    public JsonArray clear() {
        internal.clear();
        return this;
    }
}
