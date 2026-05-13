package de.joesst.dev.fulfillmenttools.facilities;

/**
 * An operational cost estimate for a facility.
 *
 * @param value the cost amount as a scaled integer
 * @param currency ISO 4217 currency code (e.g. "EUR", "USD")
 * @param decimalPlaces number of decimal places for proper currency representation
 */
public record FacilityOperativeCost(Integer value, String currency, Integer decimalPlaces) {

    /**
     * Returns a builder for constructing a {@link FacilityOperativeCost}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link FacilityOperativeCost}.
     */
    public static final class Builder {

        private Integer value;
        private String currency;
        private Integer decimalPlaces;

        private Builder() {}

        /**
         * @param value the cost amount as a scaled integer
         * @return this builder
         */
        public Builder value(Integer value) {
            this.value = value;
            return this;
        }

        /**
         * @param currency ISO 4217 currency code (e.g. "EUR", "USD")
         * @return this builder
         */
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        /**
         * @param decimalPlaces number of decimal places for proper currency representation
         * @return this builder
         */
        public Builder decimalPlaces(Integer decimalPlaces) {
            this.decimalPlaces = decimalPlaces;
            return this;
        }

        /**
         * Builds a {@link FacilityOperativeCost}.
         *
         * @return a new instance
         */
        public FacilityOperativeCost build() {
            return new FacilityOperativeCost(value, currency, decimalPlaces);
        }
    }
}
