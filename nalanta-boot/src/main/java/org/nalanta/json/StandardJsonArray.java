package org.nalanta.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

class StandardJsonArray extends AbstractJsonArray {

    StandardJsonArray() {
        super(new ArrayList<>());
    }

    @Override
    public JsonArray freeze() {
        ArrayList<JsonEntity> copiedList = new ArrayList<>(internal.size());
        for(JsonEntity jsonEntity : internal) {
            if(jsonEntity instanceof JsonObject) {
                copiedList.add(((JsonObject) jsonEntity).freeze());
            }
            else if(jsonEntity instanceof JsonArray) {
                copiedList.add(((JsonArray) jsonEntity).freeze());
            }
            else {
                copiedList.add(jsonEntity);
            }
        }
        return new ImmutableJsonArray(copiedList);
    }

    @Override
    public JsonArray share() {
        ArrayList<JsonEntity> copiedList = new ArrayList<>(internal.size());
        for(JsonEntity jsonEntity : internal) {
            if(jsonEntity instanceof JsonObject) {
                copiedList.add(((JsonObject) jsonEntity).share());
            }
            else if(jsonEntity instanceof JsonArray) {
                copiedList.add(((JsonArray) jsonEntity).share());
            }
            else {
                copiedList.add(jsonEntity);
            }
        }
        return new SharedJsonArray(copiedList);
    }

    @Override
    public boolean isImmutable() {
        return false;
    }

    @Override
    public String stringify() {
        StringBuilder sb = new StringBuilder().append('[');
        for(JsonEntity jsonEntity : internal) {
            sb.append(jsonEntity.stringify()).append(',');
        }
        if(sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.append(']').toString();
    }

    @Override
    public Iterator<JsonEntity> iterator() {
        return null;
    }

    static class StandardJsonArrayIterator implements Iterator<JsonEntity> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public JsonEntity next() {
            return null;
        }

        @Override
        public void remove() {

        }

        @Override
        public void forEachRemaining(Consumer<? super JsonEntity> action) {

        }
    }
}
