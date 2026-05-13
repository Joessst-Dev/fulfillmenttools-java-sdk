package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a user ({@code user.id} in the API).
 *
 * @param value the raw UUID string
 */
public record UserId(String value) implements PlatformId {
    public UserId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
