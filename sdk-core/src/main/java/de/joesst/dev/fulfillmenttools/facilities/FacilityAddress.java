package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.model.Coordinates;
import de.joesst.dev.fulfillmenttools.model.TimeZone;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;

/**
 * Physical address and contact information for a facility.
 *
 * @param street street name
 * @param houseNumber street number or building identifier
 * @param city city name
 * @param postalCode zip or postal code
 * @param country ISO 3166-1 alpha-2 country code
 * @param additionalAddressInfo supplementary address details (e.g. floor, suite)
 * @param province state or province code
 * @param companyName registered company or organization name
 * @param phoneNumbers list of phone numbers for contacting the facility
 * @param emailAddresses list of email addresses for contacting the facility
 * @param resolvedCoordinates latitude and longitude if geocoded
 * @param resolvedTimeZone IANA time zone identifier
 * @param customAttributes free-form custom attributes
 */
public record FacilityAddress(
        String street,
        String houseNumber,
        String city,
        String postalCode,
        String country,
        String additionalAddressInfo,
        String province,
        String companyName,
        List<FacilityPhoneNumber> phoneNumbers,
        List<FacilityEmailAddress> emailAddresses,
        Coordinates resolvedCoordinates,
        TimeZone resolvedTimeZone,
        CustomAttributes customAttributes
) {

    /**
     * Returns a builder for constructing a {@link FacilityAddress}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link FacilityAddress}.
     */
    public static final class Builder {

        private String street;
        private String houseNumber;
        private String city;
        private String postalCode;
        private String country;
        private String additionalAddressInfo;
        private String province;
        private String companyName;
        private List<FacilityPhoneNumber> phoneNumbers;
        private List<FacilityEmailAddress> emailAddresses;
        private Coordinates resolvedCoordinates;
        private TimeZone resolvedTimeZone;
        private CustomAttributes customAttributes;

        private Builder() {}

        /**
         * @param street street name
         * @return this builder
         */
        public Builder street(String street) {
            this.street = street;
            return this;
        }

        /**
         * @param houseNumber street number or building identifier
         * @return this builder
         */
        public Builder houseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        /**
         * @param city city name
         * @return this builder
         */
        public Builder city(String city) {
            this.city = city;
            return this;
        }

        /**
         * @param postalCode zip or postal code
         * @return this builder
         */
        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * @param country ISO 3166-1 alpha-2 country code
         * @return this builder
         */
        public Builder country(String country) {
            this.country = country;
            return this;
        }

        /**
         * @param additionalAddressInfo supplementary address details (e.g. floor, suite)
         * @return this builder
         */
        public Builder additionalAddressInfo(String additionalAddressInfo) {
            this.additionalAddressInfo = additionalAddressInfo;
            return this;
        }

        /**
         * @param province state or province code
         * @return this builder
         */
        public Builder province(String province) {
            this.province = province;
            return this;
        }

        /**
         * @param companyName registered company or organization name
         * @return this builder
         */
        public Builder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        /**
         * @param phoneNumbers list of phone numbers for contacting the facility
         * @return this builder
         */
        public Builder phoneNumbers(List<FacilityPhoneNumber> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
            return this;
        }

        /**
         * @param emailAddresses list of email addresses for contacting the facility
         * @return this builder
         */
        public Builder emailAddresses(List<FacilityEmailAddress> emailAddresses) {
            this.emailAddresses = emailAddresses;
            return this;
        }

        /**
         * @param resolvedCoordinates latitude and longitude if geocoded
         * @return this builder
         */
        public Builder resolvedCoordinates(Coordinates resolvedCoordinates) {
            this.resolvedCoordinates = resolvedCoordinates;
            return this;
        }

        /**
         * @param resolvedTimeZone IANA time zone identifier
         * @return this builder
         */
        public Builder resolvedTimeZone(TimeZone resolvedTimeZone) {
            this.resolvedTimeZone = resolvedTimeZone;
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
         * Builds a {@link FacilityAddress}.
         *
         * @return a new instance
         */
        public FacilityAddress build() {
            return new FacilityAddress(
                    street, houseNumber, city, postalCode, country, additionalAddressInfo,
                    province, companyName, phoneNumbers, emailAddresses,
                    resolvedCoordinates, resolvedTimeZone, customAttributes);
        }
    }
}
