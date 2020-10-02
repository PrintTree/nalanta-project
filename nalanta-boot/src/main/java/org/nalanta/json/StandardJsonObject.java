package org.nalanta.json;

import java.util.LinkedHashMap;

class StandardJsonObject extends AbstractJsonObject {

    StandardJsonObject() {
        super(new LinkedHashMap<>(8));
    }

    @Override
    public boolean isImmutable() {
        return false;
    }

}
