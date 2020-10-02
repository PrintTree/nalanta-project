package org.nalanta.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

final class JsonUtil {

    private JsonUtil() {}

    static ObjectMapper objectMapper = new ObjectMapper();
    static String testJsonString = "{\"name\":\"Alice\",\"code\":2.75,\"dept\":{},\"attr\":[\"u1\",{\"k\":\"v\"},true,3],\"test\":null}";
    static {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(JsonEntity.class, new JsonEntityDeserializer());
        objectMapper.registerModule(module);
    }

    public static void main(String[] args) throws Exception {

        //JsonEntity jsonEntity = objectMapper.readValue(testJsonString, JsonEntity.class);
        //System.out.println(jsonEntity.stringify());

        /*long t1 = System.currentTimeMillis();
        for(int i = 0; i < 100000; i++) {
            JsonObject jsonObject = JsonObject.from(testJsonString);
            jsonObject.stringify();
        }
        System.out.println(System.currentTimeMillis() - t1);*/
        JsonObject jsonObject = JsonObject.from(testJsonString);
        System.out.println(jsonObject.stringify());
    }

    static JsonObject parseObject(String jsonString) {
        return (JsonObject) parseEntity(jsonString);
    }

    static JsonArray parseArray(String jsonString) {
        return (JsonArray) parseEntity(jsonString);
    }

    static JsonEntity parseEntity(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, JsonEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static class JsonEntityDeserializer extends JsonDeserializer<JsonEntity> {

        @Override
        public JsonEntity deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonEntity jsonEntity;
            JsonToken currentToken = p.currentToken();
            switch (currentToken) {
                case START_OBJECT:
                    jsonEntity = parseJsonObject(p, ctxt);
                    break;
                case START_ARRAY:
                    jsonEntity = parseJsonArray(p, ctxt);
                    break;
                case VALUE_STRING:
                    jsonEntity = parseJsonString(p, ctxt);
                    break;
                case VALUE_NUMBER_INT:
                case VALUE_NUMBER_FLOAT:
                    jsonEntity = parseJsonNumber(p, ctxt);
                    break;
                case VALUE_TRUE:
                case VALUE_FALSE:
                    jsonEntity = parseJsonBoolean(p, ctxt);
                    break;
                case VALUE_NULL:
                    jsonEntity = parseJsonNull(p, ctxt);
                    break;
                default:
                    throw new IllegalStateException("unsupported json token: " + currentToken);
            }
            return jsonEntity;
        }

        private JsonObject parseJsonObject(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonObject jsonObject = new StandardJsonObject();
            JsonToken jsonToken = p.nextToken();
            if(jsonToken == JsonToken.END_OBJECT) {
                return jsonObject;
            }
            String key = p.getCurrentName();
            for(; key != null; key = p.nextFieldName()) {
                p.nextToken();
                jsonObject.put(key, deserialize(p, ctxt));
            }
            return jsonObject;
        }

        private JsonArray parseJsonArray(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonArray jsonArray = new StandardJsonArray();
            JsonToken jsonToken = p.nextToken();
            if(jsonToken == JsonToken.END_ARRAY) {
                return jsonArray;
            }
            do {
                jsonArray.add(deserialize(p, ctxt));
            } while (p.nextToken() != JsonToken.END_ARRAY);
            return jsonArray;
        }

        private JsonString parseJsonString(JsonParser p, DeserializationContext ctxt) throws IOException {
            String s = p.getValueAsString();
            return new JsonString(s);
        }

        private JsonNumber parseJsonNumber(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonToken currentToken = p.currentToken();
            if(currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
                return new JsonNumber(p.getValueAsDouble());
            }
            if(currentToken == JsonToken.VALUE_NUMBER_INT) {
                return new JsonNumber(p.getValueAsLong());
            }
            return new JsonNumber(p.getNumberValue());
        }

        private JsonBoolean parseJsonBoolean(JsonParser p, DeserializationContext ctxt) throws IOException {
            return new JsonBoolean(p.getValueAsBoolean());
        }

        private JsonNull parseJsonNull(JsonParser p, DeserializationContext ctxt) throws IOException {
            return new JsonNull();
        }
    }

}
