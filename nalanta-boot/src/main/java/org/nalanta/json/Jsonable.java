package org.nalanta.json;

public interface Jsonable<T> {

    JsonObject json();

    T from(String jsonString);

}
