package org.nalanta.core.json;

import java.util.List;

public class JsonArray {

    public JsonArray from(String jsonString) {
        return this;
    }

    public JsonArray from(List<Object> jsonList) {
        return this;
    }

    public String stringify() {
        return null;
    }

    public String string(int index) {
        return null;
    }

    public JsonArray string(int index, String value) {
        return this;
    }

}
