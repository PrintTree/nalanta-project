package org.nalanta.json;

import java.util.LinkedHashMap;

class StandardJsonObject extends AbstractJsonObject {

    StandardJsonObject() {
        super(new LinkedHashMap<>(8));
    }

    @Override
    public JsonObject freeze() {
        return new ImmutableJsonObject(internal);
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
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

}
