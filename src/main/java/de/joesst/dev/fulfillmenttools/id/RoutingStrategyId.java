package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a routing strategy ({@code routingStrategy.id} in the API).
 *
 * @param value the raw UUID string
 */
public record RoutingStrategyId(String value) implements PlatformId {
    public RoutingStrategyId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
