package org.nalanta.json;

public class JsonString implements JsonEntity {

    private final String internal;

    public JsonString(String s) {
        internal = s;
    }

    @Override
    public boolean isImmutable() {
        return true;
    }

    @Override
    public Type type() {
        return Type.STRING;
    }

    @Override
    public String stringify() {
        return "\"" + internal + "\"";
    }

    @Override
    public Object simplify() {
        return internal;
    }

    public String internalString() {
        return internal;
    }
}
