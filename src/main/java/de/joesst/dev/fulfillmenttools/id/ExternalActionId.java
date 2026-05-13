package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for an external action ({@code externalAction.id} in the API).
 *
 * @param value the raw UUID string
 */
public record ExternalActionId(String value) implements PlatformId {
    public ExternalActionId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
