package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a carrier ({@code carrier.id} in the API).
 *
 * @param value the raw UUID string
 */
public record CarrierId(String value) implements PlatformId {
    public CarrierId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
