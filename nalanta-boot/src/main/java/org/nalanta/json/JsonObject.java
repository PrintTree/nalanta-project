package org.nalanta.json;

public interface JsonObject extends JsonEntity {

    static JsonObject from(String jsonString) {
        return null;
    }

    static JsonObject create() {
        return null;
    }

    JsonObject freeze();

    JsonObject getJsonObject(String key);

    JsonArray getJsonArray(String key);

    JsonString getJsonString(String key);

    JsonNumber getJsonNumber(String key);

    JsonBoolean getJsonBoolean(String key);

    Boolean getBoolean(String key);

    String getString(String key);

    Long getNumberAsLong(String key);

    Integer getNumberAsInteger(String key);

    Short getNumberAsShort(String key);

    Byte getNumberAsByte(String key);

    Double getNumberAsDouble(String key);

    Float getNumberAsFloat(String key);
}
