package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * A phone number entry on a consumer address.
 *
 * <p>Maps to the inline phone-number object defined in {@code Address.phoneNumbers} in the
 * fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param value The phone number string. No particular format is enforced by the API.
 * @param type  The type of this phone number: {@code MOBILE} or {@code PHONE}.
 * @param label Optional human-readable label (e.g. "private", "business").
 * @param customAttributes Optional free-form custom attributes attached to this phone number.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PhoneNumber(
        String value,
        String type,
        String label,
        CustomAttributes customAttributes
) {

    /**
     * Returns a builder for constructing a {@code PhoneNumber}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link PhoneNumber}.
     */
    public static final class Builder {

        private String value;
        private String type;
        private String label;
        private CustomAttributes customAttributes;

        private Builder() {}

        /**
         * Sets the phone number string.
         * @param value the phone number value
         * @return this builder
         */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        /**
         * Sets the type of this phone number.
         * @param type the phone number type ({@code MOBILE} or {@code PHONE})
         * @return this builder
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the optional human-readable label.
         * @param label the label (e.g. {@code private}, {@code business})
         * @return this builder
         */
        public Builder label(String label) {
            this.label = label;
            return this;
        }

        /**
         * Sets optional free-form custom attributes attached to this phone number.
         * @param customAttributes the custom attributes map
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        /**
         * Builds a {@link PhoneNumber}.
         *
         * @return a new instance.
         */
        public PhoneNumber build() {
            return new PhoneNumber(value, type, label, customAttributes);
        }
    }
}
