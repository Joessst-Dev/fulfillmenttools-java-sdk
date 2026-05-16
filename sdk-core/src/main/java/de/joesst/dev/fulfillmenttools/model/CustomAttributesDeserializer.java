package de.joesst.dev.fulfillmenttools.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Map;

final class CustomAttributesDeserializer extends StdDeserializer<CustomAttributes> {

    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {};

    private final ObjectMapper mapper;

    CustomAttributesDeserializer(ObjectMapper mapper) {
        super(CustomAttributes.class);
        this.mapper = mapper;
    }

    @Override
    public CustomAttributes deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Map<String, Object> attributes = p.readValueAs(MAP_TYPE);
        return new CustomAttributes(attributes, mapper);
    }
}
