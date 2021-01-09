package org.nalanta.json;

import java.util.List;

import static org.nalanta.json.JsonEntity.Type.ARRAY;

public interface JsonArray extends JsonEntity {

    static JsonArray from(String jsonString) {
        return JsonUtil.parseArray(jsonString);
    }

    static JsonArray from(List<?> list) {
        JsonArray parsed = null;
        try {
            parsed = JsonUtil.parseArray(list);
        } catch (Exception ignored) {
            //warn log
        }
        return parsed == null ? create() : parsed;
    }

    static JsonArray from(Object in) {
        if (in instanceof String) {
            return from((String) in);
        } else if (in instanceof List) {
            return from((List<?>) in);
        } else {
            //warn log
            return create();
        }
    }

    static JsonArray create() {
        return new StandardJsonArray();
    }

    @Override
    default Type type() {
        return ARRAY;
    }

    JsonArray copy();

    JsonArray freeze();

    JsonArray share();

    int size();

    JsonArray add(Object element);

    JsonArray add(JsonEntity element);

    JsonArray add(String element);

    JsonArray add(Number element);

    JsonArray add(Boolean element);

    JsonArray set(int index, Object element);

    JsonArray set(int index, JsonEntity element);

    JsonArray set(int index, String element);

    JsonArray set(int index, Number element);

    JsonArray set(int index, Boolean element);

    Object get(int index);

    JsonEntity getJsonEntity(int index);

    JsonObject getJsonObject(int index);

    JsonArray getJsonArray(int index);

    JsonString getJsonString(int index);

    JsonNumber getJsonNumber(int index);

    JsonBoolean getJsonBoolean(int index);

    String getString(int index);

    Number getNumber(int index);

    Integer getInteger(int index);

    Long getLong(int index);

    Float getFloat(int index);

    Double getDouble(int index);

    Boolean getBoolean(int index);

    JsonArray remove(int index);

    JsonEntity take(int index);

    JsonArray clear();

}
