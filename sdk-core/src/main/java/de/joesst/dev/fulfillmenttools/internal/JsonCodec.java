package de.joesst.dev.fulfillmenttools.internal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;


public final class JsonCodec {

    static final ObjectMapper SHARED_MAPPER;

    static {
        ObjectMapper m = baseConfig();
        m.registerModule(CustomAttributes.jacksonModule(m));
        SHARED_MAPPER = m;
    }

    private final ObjectMapper mapper;

    public JsonCodec() {
        this.mapper = SHARED_MAPPER;
    }

    /**
     * Creates a codec using a user-supplied mapper. A copy is made so the original is never
     * mutated, and the {@link CustomAttributes} deserializer is registered on the copy so that
     * {@link CustomAttributes#as(Class)} uses the correct mapper for API responses.
     */
    public JsonCodec(ObjectMapper userMapper) {
        ObjectMapper copy = userMapper.copy();
        copy.registerModule(CustomAttributes.jacksonModule(copy));
        this.mapper = copy;
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

    /** Converts a raw map to the specified type using the SDK's default mapper. */
    public static <T> T convert(Map<String, Object> source, Class<T> type) {
        return SHARED_MAPPER.convertValue(source, type);
    }

    private static ObjectMapper baseConfig() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }


}
