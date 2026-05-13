package de.joesst.dev.fulfillmenttools.facilityconnections;

/**
 * Represents the transit time range advertised by a carrier for a given connection or
 * packaging unit.
 *
 * @param minTransitDays minimum number of transit days
 * @param maxTransitDays maximum number of transit days
 */
public record CarrierTransitTime(Integer minTransitDays, Integer maxTransitDays) {

    /**
     * Returns a builder for constructing a {@link CarrierTransitTime}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link CarrierTransitTime}.
     */
    public static final class Builder {

        private Integer minTransitDays;
        private Integer maxTransitDays;

        private Builder() {}

        /**
         * @param minTransitDays minimum number of transit days
         * @return this builder
         */
        public Builder minTransitDays(Integer minTransitDays) {
            this.minTransitDays = minTransitDays;
            return this;
        }

        /**
         * @param maxTransitDays maximum number of transit days
         * @return this builder
         */
        public Builder maxTransitDays(Integer maxTransitDays) {
            this.maxTransitDays = maxTransitDays;
            return this;
        }

        /**
         * Builds a {@link CarrierTransitTime}.
         *
         * @return a new instance
         */
        public CarrierTransitTime build() {
            return new CarrierTransitTime(minTransitDays, maxTransitDays);
        }
    }
}
