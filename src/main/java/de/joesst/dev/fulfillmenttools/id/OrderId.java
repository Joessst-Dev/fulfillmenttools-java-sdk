package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for an order ({@code order.id} in the API).
 *
 * @param value the raw UUID string
 */
public record OrderId(String value) implements PlatformId {
    public OrderId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
