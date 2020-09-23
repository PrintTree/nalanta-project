package org.nalanta.json;

/**
 * represent json data types and other extra information.
 */
public interface JsonEntity {

    enum Type {
        OBJECT,
        ARRAY,
        INTEGER,
        DOUBLE,
        STRING,
        BOOLEAN
    }

    boolean isImmutable();

    Type type();

    String stringify();

}
