package org.nalanta.json;

public class JsonInteger implements JsonEntity {

    private final int internal;

    public JsonInteger(int i) {
        internal = i;
    }

    @Override
    public boolean isImmutable() {
        return true;
    }

    @Override
    public Type type() {
        return Type.INTEGER;
    }

    @Override
    public String stringify() {
        return Integer.toString(internal);
    }
}
