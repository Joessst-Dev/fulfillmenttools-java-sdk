package de.joesst.dev.fulfillmenttools.id;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

/**
 * Platform-assigned identifier for a listing ({@code listing.id} in the API).
 *
 * @param value the raw UUID string
 */
public record ListingId(String value) implements PlatformId {
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public ListingId(String value) {
        this.value = Objects.requireNonNull(value, "value");
    }
    @Override public String toString() { return value; }
}
