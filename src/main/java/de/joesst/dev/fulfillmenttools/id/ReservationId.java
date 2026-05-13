package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a reservation ({@code reservation.id} in the API).
 *
 * @param value the raw UUID string
 */
public record ReservationId(String value) implements PlatformId {
    public ReservationId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
