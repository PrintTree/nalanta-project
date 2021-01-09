package org.nalanta.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

final class JsonUtil {

    private JsonUtil() {}

    static ObjectMapper objectMapper = new ObjectMapper();
    static {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(JsonEntity.class, new JsonEntityDeserializer());
        objectMapper.registerModule(module);
    }

    static JsonObject parseObject(String jsonString) {
        return (JsonObject) parseEntity(jsonString);
    }

    static JsonObject parseObject(Map<String, ?> map) {
        JsonObject jsonObject = new StandardJsonObject();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            if (val instanceof String) {
                jsonObject.put(key, (String) val);
            } else if (val instanceof Number) {
                jsonObject.put(key, (Number) val);
            } else if (val instanceof Boolean) {
                jsonObject.put(key, (Boolean) val);
            } else if (val instanceof JsonEntity) {
                jsonObject.put(key, (JsonEntity) val);
            } else if (val instanceof Map) {
                try {
                    @SuppressWarnings("unchecked")
                    JsonObject nestedObject = parseObject((Map<String, ?>) val);
                    jsonObject.put(key, nestedObject);
                } catch (Exception ignored) {
                    //warn log
                }
            } else if (val instanceof List) {
                try {
                    JsonArray nestedArray = parseArray((List<?>) val);
                    jsonObject.put(key, nestedArray);
                } catch (Exception ignored) {
                    //warn log
                }
            } else {
                //warn log
            }
        }
        return jsonObject;
    }

    static JsonArray parseArray(String jsonString) {
        return (JsonArray) parseEntity(jsonString);
    }

    static JsonArray parseArray(List<?> list) {
        JsonArray jsonArray = new StandardJsonArray();
        for (Object element : list) {
            if (element instanceof String) {
                jsonArray.add((String) element);
            } else if (element instanceof Number) {
                jsonArray.add((Number) element);
            } else if (element instanceof Boolean) {
                jsonArray.add((Boolean) element);
            } else if (element instanceof JsonEntity) {
                jsonArray.add((JsonEntity) element);
            } else if (element instanceof Map) {
                try {
                    @SuppressWarnings("unchecked")
                    JsonObject nestedObject = parseObject((Map<String, ?>) element);
                    jsonArray.add(nestedObject);
                } catch (Exception ignored) {
                    //warn log
                }
            } else if (element instanceof List) {
                try {
                    JsonArray nestedArray = parseArray((List<?>) element);
                    jsonArray.add(nestedArray);
                } catch (Exception ignored) {
                    //warn log
                }
            } else {
                //warn log
            }
        }
        return jsonArray;
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
