package org.nalanta.core.json;

public interface Jsonable<T> {

    JsonObject json();

    T from(String jsonString);

}
