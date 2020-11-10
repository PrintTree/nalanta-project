package org.nalanta.json;

/**
 * represent json data types and other extra information.
 */
public interface JsonEntity {

    enum Type {
        OBJECT,
        ARRAY,
        Number,
        STRING,
        BOOLEAN,
        NULL
    }

    boolean isImmutable();

    Type type();

    String stringify();

    Object simplify();

}
