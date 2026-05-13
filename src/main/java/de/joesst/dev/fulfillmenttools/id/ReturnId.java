package de.joesst.dev.fulfillmenttools.id;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * Platform-assigned identifier for a return ({@code return.id} in the API).
 *
 * @param value the raw UUID string
 */
public record ReturnId(String value) implements PlatformId {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public ReturnId(String value) {
        this.value = Objects.requireNonNull(value, "value");
    }
    @Override public String toString() { return value; }
}
