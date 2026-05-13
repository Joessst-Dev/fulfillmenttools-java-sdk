package de.joesst.dev.fulfillmenttools.id;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * Platform-assigned identifier for an event subscription ({@code subscription.id} in the API).
 *
 * @param value the raw UUID string
 */
public record SubscriptionId(String value) implements PlatformId {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public SubscriptionId(String value) {
        this.value = Objects.requireNonNull(value, "value");
    }
    @Override public String toString() { return value; }
}
