package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for an operative process ({@code process.id} in the API).
 *
 * @param value the raw UUID string
 */
public record ProcessId(String value) implements PlatformId {
    public ProcessId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
