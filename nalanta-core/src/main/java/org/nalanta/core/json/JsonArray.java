package org.nalanta.core.json;

import java.util.List;

public class JsonArray {

    JsonArray from(String jsonString) {
        return this;
    }

    JsonArray from(List<Object> jsonList) {
        return this;
    }

    String stringify() {
        return null;
    }

}
