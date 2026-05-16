package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * Primary contact person for a facility.
 *
 * @param firstName contact person's first name
 * @param lastName contact person's family name
 * @param roleDescription job title or role description
 * @param customAttributes free-form custom attributes
 */
public record FacilityContact(String firstName, String lastName, String roleDescription, CustomAttributes customAttributes) {

    /**
     * Returns a builder for constructing a {@link FacilityContact}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link FacilityContact}.
     */
    public static final class Builder {

        private String firstName;
        private String lastName;
        private String roleDescription;
        private CustomAttributes customAttributes;

        private Builder() {}

        /**
         * @param firstName contact person's first name
         * @return this builder
         */
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * @param lastName contact person's family name
         * @return this builder
         */
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * @param roleDescription job title or role description
         * @return this builder
         */
        public Builder roleDescription(String roleDescription) {
            this.roleDescription = roleDescription;
            return this;
        }

        /**
         * @param customAttributes free-form custom attributes
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        /**
         * Builds a {@link FacilityContact}.
         *
         * @return a new instance
         */
        public FacilityContact build() {
            return new FacilityContact(firstName, lastName, roleDescription, customAttributes);
        }
    }
}
