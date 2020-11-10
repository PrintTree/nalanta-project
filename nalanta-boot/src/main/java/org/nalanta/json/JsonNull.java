package org.nalanta.json;

import static org.nalanta.json.JsonEntity.Type.NULL;

public class JsonNull implements JsonEntity {

    @Override
    public boolean isImmutable() {
        return true;
    }

    @Override
    public Type type() {
        return NULL;
    }

    @Override
    public String stringify() {
        return "null";
    }

    @Override
    public Object simplify() {
        return null;
    }
}
