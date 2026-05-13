package de.joesst.dev.fulfillmenttools.id;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Marker interface for all fulfillmenttools typed identifiers.
 *
 * <p>Sealed to exactly two categories: {@link PlatformId} for system-generated UUIDs
 * and {@link TenantId} for customer-defined identifiers.
 *
 * <p>Jackson serialization: all implementing types serialize as a plain JSON string
 * (e.g. {@code "fac-1"}) rather than a JSON object, thanks to {@link JsonValue}.
 * Deserialization is handled per-record via
 * {@code @JsonCreator(mode = JsonCreator.Mode.DELEGATING)}.
 */
public sealed interface TypedId permits PlatformId, TenantId {

    /**
     * Returns the raw string value of this identifier.
     *
     * <p>Annotated with {@link JsonValue} so that Jackson serializes every
     * implementing type as a plain JSON string rather than a JSON object.
     *
     * @return the identifier value; never {@code null}
     */
    @JsonValue
    String value();
}
