package org.nalanta.json;

class SharedJsonArray implements JsonArray {
    @Override
    public JsonObject freeze() {
        return null;
    }

    @Override
    public JsonObject share() {
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
