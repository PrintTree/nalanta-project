package org.nalanta.json;

public class JsonDouble implements JsonEntity {

    private final double internal;

    public JsonDouble(double d) {
        internal = d;
    }

    @Override
    public boolean isImmutable() {
        return true;
    }

    @Override
    public Type type() {
        return Type.DOUBLE;
    }

    @Override
    public String stringify() {
        return Double.toString(internal);
    }
}
