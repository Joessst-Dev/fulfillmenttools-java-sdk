package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a pack job ({@code packJob.id} in the API).
 *
 * @param value the raw UUID string
 */
public record PackJobId(String value) implements PlatformId {
    public PackJobId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
