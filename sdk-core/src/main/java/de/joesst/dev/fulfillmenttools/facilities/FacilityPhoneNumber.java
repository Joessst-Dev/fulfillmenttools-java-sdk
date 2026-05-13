package de.joesst.dev.fulfillmenttools.facilities;

import java.util.Map;

/**
 * A phone number with optional type and label information.
 *
 * @param value the phone number string
 * @param type classification of the phone line (e.g. "LANDLINE", "MOBILE", "FAX")
 * @param label human-readable label (e.g. "Main", "Warehouse", "Customer Service")
 * @param customAttributes free-form custom attributes
 */
public record FacilityPhoneNumber(String value, String type, String label, Map<String, Object> customAttributes) {

    /**
     * Returns a builder for constructing a {@link FacilityPhoneNumber}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link FacilityPhoneNumber}.
     */
    public static final class Builder {

        private String value;
        private String type;
        private String label;
        private Map<String, Object> customAttributes;

        private Builder() {}

        /**
         * @param value the phone number string
         * @return this builder
         */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        /**
         * @param type classification of the phone line (e.g. "LANDLINE", "MOBILE", "FAX")
         * @return this builder
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * @param label human-readable label (e.g. "Main", "Warehouse", "Customer Service")
         * @return this builder
         */
        public Builder label(String label) {
            this.label = label;
            return this;
        }

        /**
         * @param customAttributes free-form custom attributes
         * @return this builder
         */
        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        /**
         * Builds a {@link FacilityPhoneNumber}.
         *
         * @return a new instance
         */
        public FacilityPhoneNumber build() {
            return new FacilityPhoneNumber(value, type, label, customAttributes);
        }
    }
}
