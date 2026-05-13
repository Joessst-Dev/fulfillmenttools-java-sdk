package de.joesst.dev.fulfillmenttools.facilityconnections;

/**
 * A coefficient applied to delivery cost calculations, expressed per measurement unit.
 *
 * @param value           the coefficient value
 * @param measurementUnit the unit of measure the coefficient is relative to (e.g. "kg")
 */
public record DeliveryCostCoefficient(Double value, String measurementUnit) {

    /**
     * Returns a builder for constructing a {@link DeliveryCostCoefficient}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link DeliveryCostCoefficient}.
     */
    public static final class Builder {

        private Double value;
        private String measurementUnit;

        private Builder() {}

        /**
         * @param value the coefficient value
         * @return this builder
         */
        public Builder value(Double value) {
            this.value = value;
            return this;
        }

        /**
         * @param measurementUnit the unit of measure the coefficient is relative to (e.g. "kg")
         * @return this builder
         */
        public Builder measurementUnit(String measurementUnit) {
            this.measurementUnit = measurementUnit;
            return this;
        }

        /**
         * Builds a {@link DeliveryCostCoefficient}.
         *
         * @return a new instance
         */
        public DeliveryCostCoefficient build() {
            return new DeliveryCostCoefficient(value, measurementUnit);
        }
    }
}
