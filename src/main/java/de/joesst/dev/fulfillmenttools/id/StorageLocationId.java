package de.joesst.dev.fulfillmenttools.id;

import java.util.Objects;

/**
 * Platform-assigned identifier for a storage location ({@code storageLocation.id} in the API).
 *
 * @param value the raw UUID string
 */
public record StorageLocationId(String value) implements PlatformId {
    public StorageLocationId { Objects.requireNonNull(value, "value"); }
    @Override public String toString() { return value; }
}
