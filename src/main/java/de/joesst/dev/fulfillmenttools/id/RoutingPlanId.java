package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a routing plan ({@code routingPlan.id} in the API).
 *
 * @param value the raw UUID string
 */
public record RoutingPlanId(String value) implements PlatformId {
    public RoutingPlanId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
