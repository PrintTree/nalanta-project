package org.nalanta.json;

import static org.nalanta.constant.Common.TRUE;
import static org.nalanta.constant.Common.FALSE;
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
        return internal ? TRUE : FALSE;
    }
}
