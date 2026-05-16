package de.joesst.dev.fulfillmenttools.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.joesst.dev.fulfillmenttools.internal.JsonCodec;

import java.util.Collections;
import java.util.Map;

/**
 * Tenant-defined extension attributes attached to a resource.
 *
 * <p>Wraps the raw {@code Map<String, Object>} returned by the API and provides a typed
 * conversion helper. Use {@link #as(Class)} to map the attributes into your own POJO using
 * the SDK's configured Jackson {@code ObjectMapper}.
 *
 * <p>Deserializes transparently from and serializes back to a plain JSON object.
 */
public final class CustomAttributes {

    private final Map<String, Object> attributes;
    @JsonIgnore
    private final ObjectMapper mapper;

    /** Used by {@link CustomAttributesDeserializer} to inject the active mapper. */
    CustomAttributes(Map<String, Object> attributes, ObjectMapper mapper) {
        this.attributes = attributes != null ? Collections.unmodifiableMap(attributes) : Collections.emptyMap();
        this.mapper = mapper;
    }

    /**
     * Creates a {@code CustomAttributes} instance for use outside of SDK deserialization
     * (e.g. building a request). Uses the SDK's default mapper for {@link #as(Class)}.
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public CustomAttributes(Map<String, Object> attributes) {
        this(attributes, null);
    }

    /**
     * Convenience factory — equivalent to {@code new CustomAttributes(attributes)}.
     *
     * @param attributes the raw attributes map; {@code null} is treated as an empty map
     * @return a new {@code CustomAttributes} instance
     */
    public static CustomAttributes of(Map<String, Object> attributes) {
        return new CustomAttributes(attributes);
    }

    /**
     * Returns the raw attributes map.
     */
    @JsonValue
    public Map<String, Object> attributes() {
        return attributes;
    }

    /**
     * Converts the attributes to the specified type using the mapper active when this instance
     * was created — the SDK's configured mapper for API responses, or the default mapper for
     * manually constructed instances.
     *
     * <p>Equivalent to {@code mapper.convertValue(attributes, type)} — no I/O is performed.
     *
     * @param type the target class
     * @param <T>  the target type
     * @return a new instance of {@code type} populated from the attributes map
     * @throws IllegalArgumentException if the attributes cannot be converted to the requested type
     */
    public <T> T as(Class<T> type) {
        if (mapper != null) {
            return mapper.convertValue(attributes, type);
        }
        return JsonCodec.convert(attributes, type);
    }

    /**
     * Returns a Jackson {@link SimpleModule} that registers a deserializer for
     * {@link CustomAttributes} bound to the supplied mapper. Register this on any
     * {@code ObjectMapper} that deserializes {@link CustomAttributes} fields so that
     * {@link #as(Class)} uses that same mapper for type conversion.
     */
    public static SimpleModule jacksonModule(ObjectMapper mapper) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CustomAttributes.class, new CustomAttributesDeserializer(mapper));
        return module;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomAttributes other)) return false;
        return attributes.equals(other.attributes);
    }

    @Override
    public int hashCode() {
        return attributes.hashCode();
    }

    @Override
    public String toString() {
        return attributes.toString();
    }
}
