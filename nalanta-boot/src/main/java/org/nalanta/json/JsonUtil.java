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

    //private ObjectMapper objectMapper = new ObjectMapper();

    static JsonObject freezeJsonObject(JsonObject resource) {

        return null;
    }

    static JsonArray freezeJsonArray(JsonArray resource) {
        return null;
    }



    static ObjectMapper objectMapper = new ObjectMapper();
    static String testJsonString = "{\"name\":\"Alice\",\"role\":\"user\",\"code\":2.75,\"dept\":{\"name\":\"FBI\"}}";
    static {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(JsonEntity.class, new JsonEntityDeserializer());
        objectMapper.registerModule(module);
    }

    public static void main(String[] args) throws Exception {
        //JsonObject jsonObject = (JsonObject) objectMapper.readValue(testJsonString, JsonEntity.class);
        //System.out.println(jsonObject);
        JsonEntity jsonEntity = objectMapper.readValue(testJsonString, JsonEntity.class);
        System.out.println(jsonEntity);
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
                case VALUE_NUMBER_FLOAT:
                    jsonEntity = parseJsonNumber(p, ctxt);
                    break;
                case VALUE_TRUE:
                case VALUE_FALSE:
                    jsonEntity = parseJsonBoolean(p, ctxt);
                    break;
                case VALUE_NULL:
                    jsonEntity = null;
                default:
                    throw new IllegalStateException("unsupported json token: " + currentToken);
            }
            return jsonEntity;
        }

        private JsonObject parseJsonObject(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonObject jsonObject = new StandardJsonObject();
            for(String key = p.nextFieldName(); key != null; key = p.nextFieldName()) {
                p.nextToken();
                JsonEntity jsonEntity = deserialize(p, ctxt);
                switch (jsonEntity.type()) {
                    case OBJECT:
                        jsonObject.putJsonObject(key, (JsonObject) jsonEntity);
                        break;
                    case ARRAY:
                        jsonObject.putJsonArray(key, (JsonArray) jsonEntity);
                        break;
                    case BOOLEAN:
                        jsonObject.putJsonBoolean(key, (JsonBoolean) jsonEntity);
                        break;
                    case STRING:
                        jsonObject.putJsonString(key, (JsonString) jsonEntity);
                        break;
                    case Number:
                        jsonObject.putJsonNumber(key, (JsonNumber) jsonEntity);
                    default:
                }
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
                JsonEntity jsonEntity = deserialize(p, ctxt);
            } while (p.nextToken() != JsonToken.END_ARRAY);
            return jsonArray;
            //TODO add element
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
    }

}
