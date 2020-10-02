package org.nalanta.session;

import org.nalanta.json.JsonEntity;

public interface Session {

    /**
     * @return the session id
     */
    String id();

    /**
     * destroy this session
     * @return session id
     */
    String destroy();

    /**
     * get this session's expire time
     * @return
     */
    long expireTime();

    /**
     * get a modifiable string attribute
     * @param key attribute key
     * @return attribute value0
     */
    String getAttribute(String key);

    /**
     * get a modifiable string attribute
     * @param key attribute key
     * @param value attribute value
     * @return attribute value
     */
    String setAttribute(String key, String value);

    /**
     * set a modifiable string attribute
     * @param key attribute key
     * @return attribute value
     */
    String removeAttribute(String key);

    /**
     * get an immutable JsonEntity attachment
     * @param key attachment key
     * @return attachment JsonEntity
     */
    JsonEntity getAttachment(String key);

    /**
     * set an immutable JsonEntity attachment.
     * If there is already exists an attachment, return null.
     * @param key attachment key
     * @param attachment attachment JsonEntity
     * @return attachment JsonEntity or null
     */
    JsonEntity setAttachment(String key, JsonEntity attachment);
}
