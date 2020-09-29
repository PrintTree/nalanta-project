package org.nalanta.json;

import java.util.Iterator;
import java.util.List;

class SharedJsonArray extends AbstractJsonArray {

    SharedJsonArray(List<JsonEntity> list) {
        super(list);
    }

    @Override
    public synchronized JsonArray freeze() {
        return null;
    }

    @Override
    public synchronized JsonArray share() {
        return null;
    }

    @Override
    public synchronized boolean isImmutable() {
        return false;
    }

    @Override
    public synchronized String stringify() {
        return null;
    }

    @Override
    public synchronized Iterator<JsonEntity> iterator() {
        return null;
    }
}
