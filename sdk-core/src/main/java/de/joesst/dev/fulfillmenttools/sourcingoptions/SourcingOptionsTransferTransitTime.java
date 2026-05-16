package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Transit time bounds for a packaging unit.
 *
 * <p>Maps to the {@code SourcingOptionsTransferTransitTime} schema in the fulfillmenttools
 * OpenAPI specification.
 *
 * @param minTransitDays minimum transit days
 * @param maxTransitDays maximum transit days
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionsTransferTransitTime(
        Double minTransitDays,
        Double maxTransitDays
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Double minTransitDays;
        private Double maxTransitDays;

        public Builder minTransitDays(Double minTransitDays) { this.minTransitDays = minTransitDays; return this; }
        public Builder maxTransitDays(Double maxTransitDays) { this.maxTransitDays = maxTransitDays; return this; }

        public SourcingOptionsTransferTransitTime build() {
            return new SourcingOptionsTransferTransitTime(minTransitDays, maxTransitDays);
        }
    }
}
