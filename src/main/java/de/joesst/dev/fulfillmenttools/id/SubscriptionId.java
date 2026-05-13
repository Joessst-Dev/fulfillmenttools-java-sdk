package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for an event subscription ({@code subscription.id} in the API).
 *
 * @param value the raw UUID string
 */
public record SubscriptionId(String value) implements PlatformId {
    public SubscriptionId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
