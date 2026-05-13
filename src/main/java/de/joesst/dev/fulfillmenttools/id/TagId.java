package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a tag ({@code tag.id} in the API).
 *
 * @param value the raw UUID string
 */
public record TagId(String value) implements PlatformId {
    public TagId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
