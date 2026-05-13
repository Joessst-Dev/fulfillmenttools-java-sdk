package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a handover job ({@code handoverJob.id} in the API).
 *
 * @param value the raw UUID string
 */
public record HandoverJobId(String value) implements PlatformId {
    public HandoverJobId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
