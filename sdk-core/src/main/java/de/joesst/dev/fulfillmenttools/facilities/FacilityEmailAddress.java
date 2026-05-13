package de.joesst.dev.fulfillmenttools.facilities;

/**
 * An email address with an optional recipient type classification.
 *
 * @param value the email address string
 * @param recipient classification of who should receive emails at this address (e.g. "Manager", "Warehouse")
 */
public record FacilityEmailAddress(String value, String recipient) {

    /**
     * Returns a builder for constructing a {@link FacilityEmailAddress}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link FacilityEmailAddress}.
     */
    public static final class Builder {

        private String value;
        private String recipient;

        private Builder() {}

        /**
         * @param value the email address string
         * @return this builder
         */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        /**
         * @param recipient classification of who should receive emails at this address
         * @return this builder
         */
        public Builder recipient(String recipient) {
            this.recipient = recipient;
            return this;
        }

        /**
         * Builds a {@link FacilityEmailAddress}.
         *
         * @return a new instance
         */
        public FacilityEmailAddress build() {
            return new FacilityEmailAddress(value, recipient);
        }
    }
}
