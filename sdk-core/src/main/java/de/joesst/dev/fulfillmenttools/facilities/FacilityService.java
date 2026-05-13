package de.joesst.dev.fulfillmenttools.facilities;

/**
 * Represents a fulfillment service offered by a facility.
 *
 * @param type service type identifier (e.g. "PICKING", "PACKING", "STORAGE")
 */
public record FacilityService(String type) {

    /**
     * Returns a builder for constructing a {@link FacilityService}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link FacilityService}.
     */
    public static final class Builder {

        private String type;

        private Builder() {}

        /**
         * @param type service type identifier (e.g. "PICKING", "PACKING", "STORAGE")
         * @return this builder
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * Builds a {@link FacilityService}.
         *
         * @return a new instance
         */
        public FacilityService build() {
            return new FacilityService(type);
        }
    }
}
