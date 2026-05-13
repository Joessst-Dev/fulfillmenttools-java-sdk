package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a facility connection ({@code facilityConnection.id} in the API).
 *
 * @param value the raw UUID string
 */
public record ConnectionId(String value) implements PlatformId {
    public ConnectionId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
