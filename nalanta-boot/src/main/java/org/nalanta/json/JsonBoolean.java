package org.nalanta.json;

import static org.nalanta.constant.Common.TRUE_STRING;
import static org.nalanta.constant.Common.FALSE_STRING;
import static org.nalanta.json.JsonEntity.Type.BOOLEAN;

public class JsonBoolean implements JsonEntity {

    private final boolean internal;

    public JsonBoolean(boolean b) {
        internal = b;
    }

    @Override
    public boolean isImmutable() {
        return true;
    }

    @Override
    public Type type() {
        return BOOLEAN;
    }

    @Override
    public String stringify() {
        return internal ? TRUE_STRING : FALSE_STRING;
    }

    public Boolean internalBoolean() {
        return internal;
    }
}
