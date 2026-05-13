package de.joesst.dev.fulfillmenttools.routingplans;

import java.time.Instant;

/**
 * A time frame indicating when an article becomes available.
 *
 * <p>Maps to the {@code AvailabilityTimeframe} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param start The start of the availability window.
 */
public record AvailabilityTimeframe(
        Instant start
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Instant start;

        private Builder() {}

        public Builder start(Instant start) { this.start = start; return this; }

        public AvailabilityTimeframe build() {
            return new AvailabilityTimeframe(start);
        }
    }
}
