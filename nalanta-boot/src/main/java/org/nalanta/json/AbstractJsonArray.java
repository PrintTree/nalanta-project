package org.nalanta.json;

import java.util.Iterator;
import java.util.List;

public abstract class AbstractJsonArray implements JsonArray {

    final List<JsonEntity> internal;

    AbstractJsonArray(List<JsonEntity> list) {
        internal = list;
    }

    @Override
    public int size() {
        return internal.size();
    }

    //TODO iterator
    @Override
    public Iterator<JsonEntity> iterator() {
        return null;
    }
}
