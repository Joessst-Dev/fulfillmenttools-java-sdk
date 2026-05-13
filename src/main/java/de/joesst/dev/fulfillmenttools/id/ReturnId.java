package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a return ({@code return.id} in the API).
 *
 * @param value the raw UUID string
 */
public record ReturnId(String value) implements PlatformId {
    public ReturnId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
