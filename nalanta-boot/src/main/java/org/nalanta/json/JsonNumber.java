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

    @Override
    public Object simplify() {
        return internal;
    }

    public Number internalNumber() {
        return internal;
    }

    public Long internalLong() {
        if(internal instanceof Long) {
            return (Long) internal;
        }
        return internal.longValue();
    }

    public Integer internalInteger() {
        if(internal instanceof Integer) {
            return (Integer) internal;
        }
        return internal.intValue();
    }

    public Double internalDouble() {
        if(internal instanceof Double) {
            return (Double) internal;
        }
        return internal.doubleValue();
    }

    public Float internalFloat() {
        if(internal instanceof Float) {
            return (Float) internal;
        }
        return internal.floatValue();
    }

    public Class<? extends Number> getNumberType() {
        return internal.getClass();
    }

}
