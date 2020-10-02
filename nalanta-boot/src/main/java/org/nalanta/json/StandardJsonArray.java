package org.nalanta.json;

import java.util.ArrayList;

class StandardJsonArray extends AbstractJsonArray {

    StandardJsonArray() {
        super(new ArrayList<>());
    }

    @Override
    public boolean isImmutable() {
        return false;
    }

}
