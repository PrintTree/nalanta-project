package org.nalanta.json;

import static org.nalanta.json.JsonEntity.Type.ARRAY;

public interface JsonArray extends JsonEntity, Iterable<JsonEntity> {

    static JsonArray from(String jsonString) {
        return JsonUtil.parseArray(jsonString);
    }

    static JsonArray create() {
        return new StandardJsonArray();
    }

    @Override
    default Type type() {
        return ARRAY;
    }

    JsonArray freeze();

    JsonArray share();

    int size();

    JsonArray add(Object element);

    JsonArray add(JsonEntity element);

    JsonArray add(String element);

    JsonArray add(Number element);

    JsonArray add(Boolean element);

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

}
