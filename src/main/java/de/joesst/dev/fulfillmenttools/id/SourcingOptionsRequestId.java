package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a sourcing options request
 * ({@code sourcingOptionsRequest.id} in the API).
 *
 * @param value the raw UUID string
 */
public record SourcingOptionsRequestId(String value) implements PlatformId {
    public SourcingOptionsRequestId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
