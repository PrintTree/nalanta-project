package org.nalanta.session;

import org.nalanta.json.JsonObject;

public interface Session {

    /**
     * @return the session id
     */
    String id();

    /**
     * get a modifiable string attribute
     * @param key attribute key
     * @return attribute value0
     */
    String attribute(String key);

    /**
     * get or remove a modifiable string attribute
     * @param key attribute key
     * @param remove remove if true, otherwise get only
     * @return attribute value
     */
    String attribute(String key, boolean remove);

    /**
     * set a modifiable string attribute
     * @param key attribute key
     * @param value attribute value
     * @return session object
     */
    Session attribute(String key, String value);

    /**
     * get an immutable JsonObject attachment
     * @param key attachment key
     * @return attachment JsonObject
     */
    JsonObject attachment(String key);

    /**
     * set an immutable JsonObject attachment
     * @param key attachment key
     * @param attachment attachment JsonObject
     * @return session object
     */
    Session attachment(String key, JsonObject attachment);



}
