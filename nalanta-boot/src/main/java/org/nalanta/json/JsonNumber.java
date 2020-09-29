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

    Number internalNumber() {
        return internal;
    }

    Long internalLong() {
        if(internal instanceof Long) {
            return (Long) internal;
        }
        return internal.longValue();
    }

    Integer internalInteger() {
        if(internal instanceof Integer) {
            return (Integer) internal;
        }
        return internal.intValue();
    }

    Double internalDouble() {
        if(internal instanceof Double) {
            return (Double) internal;
        }
        return internal.doubleValue();
    }

    Float internalFloat() {
        if(internal instanceof Float) {
            return (Float) internal;
        }
        return internal.floatValue();
    }

    public Class<? extends Number> getNumberType() {
        return internal.getClass();
    }

}
