package org.nalanta.json;

public class StandardJsonArray implements JsonArray {

    @Override
    public JsonObject freeze() {
        return null;
    }

    @Override
    public boolean isImmutable() {
        return false;
    }

    @Override
    public String stringify() {
        return null;
    }
}
