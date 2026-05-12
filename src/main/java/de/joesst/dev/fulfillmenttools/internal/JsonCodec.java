package de.joesst.dev.fulfillmenttools.internal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.UncheckedIOException;

public final class JsonCodec {

    private final ObjectMapper mapper;

    public JsonCodec() {
        this(defaultMapper());
    }

    public JsonCodec(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T decode(byte[] body, Class<T> type) {
        try {
            return mapper.readValue(body, type);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to deserialize response body", e);
        }
    }

    public <T> T decode(byte[] body, TypeReference<T> typeRef) {
        try {
            return mapper.readValue(body, typeRef);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to deserialize response body", e);
        }
    }

    public JsonNode decodeTree(byte[] body) {
        try {
            return mapper.readTree(body);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to parse response body as JSON tree", e);
        }
    }

    public byte[] encode(Object value) {
        try {
            return mapper.writeValueAsBytes(value);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to serialize request body", e);
        }
    }

    private static ObjectMapper defaultMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }
}
