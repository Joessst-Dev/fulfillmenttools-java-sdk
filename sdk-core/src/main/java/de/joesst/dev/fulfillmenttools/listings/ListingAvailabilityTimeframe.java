package de.joesst.dev.fulfillmenttools.listings;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * Defines the start of an availability window for a listing.
 *
 * <p>Maps to the {@code AvailabilityTimeframe} schema in the fulfillmenttools OpenAPI spec.
 * This field is deprecated as of 26 February 2025 — use {@code outOfStockConfig} instead.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param start The start of the availability window.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListingAvailabilityTimeframe(
        Instant start
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Instant start;

        private Builder() {}

        public Builder start(Instant start) { this.start = start; return this; }

        public ListingAvailabilityTimeframe build() {
            return new ListingAvailabilityTimeframe(start);
        }
    }
}
