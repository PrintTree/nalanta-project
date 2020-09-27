package org.nalanta.json;

public class JsonNumber implements JsonEntity {

    private final Number internal;

    public JsonNumber(Number d) {
        internal = d;
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
        //TODO
        return internal.toString();
    }

    Long internalLong() {
        return internal.longValue();
    }

    Integer internalInteger() {
        return internal.intValue();
    }

    Double internalDouble() {
        return internal.doubleValue();
    }

    Float internalFloat() {
        return internal.floatValue();
    }

}
