package org.nalanta.json;

public class JsonNumber implements JsonEntity {

    private final String internal;

    private final boolean isInteger;

    public JsonNumber(String d) {
        internal = d;
        isInteger = !d.contains(".");
    }

    @Override
    public boolean isImmutable() {
        return true;
    }

    @Override
    public Type type() {
        return Type.Number;
    }

    @Override
    public String stringify() {
        return internal;
    }

}
