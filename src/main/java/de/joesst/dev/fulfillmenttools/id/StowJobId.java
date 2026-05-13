package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a stow job ({@code stowJob.id} in the API).
 *
 * @param value the raw UUID string
 */
public record StowJobId(String value) implements PlatformId {
    public StowJobId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
