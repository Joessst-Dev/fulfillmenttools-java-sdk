package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.model.Coordinates;

import java.util.List;
import java.util.Map;

/**
 * A consumer address used on an order.
 *
 * <p>Maps to the {@code ConsumerAddress} schema in the fulfillmenttools OpenAPI spec.
 * At minimum {@code street}, {@code city}, {@code postalCode}, and {@code country} are required
 * per the base {@code Address} schema.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param street               The street name.
 * @param houseNumber          The house number.
 * @param city                 The city.
 * @param postalCode           The postal code.
 * @param country              ISO 3166-1 alpha-2 two-letter country code (e.g. {@code DE}).
 * @param additionalAddressInfo Optional supplementary address information.
 * @param province             Optional province or state.
 * @param firstName            Optional first name of the recipient.
 * @param lastName             Optional last name of the recipient.
 * @param personalTitle        Optional personal title (e.g. {@code Dr.}).
 * @param salutation           Optional salutation.
 * @param companyName          Optional company name.
 * @param email                Optional email address.
 * @param addressType          The address type: {@code POSTAL_ADDRESS}, {@code PARCEL_LOCKER},
 *                             or {@code INVOICE_ADDRESS}.
 * @param coordinates          Optional geo-coordinates for the address.
 * @param phoneNumbers         Optional list of phone numbers for the recipient.
 * @param customAttributes     Free-form custom attributes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConsumerAddress(
        String street,
        String houseNumber,
        String city,
        String postalCode,
        String country,
        String additionalAddressInfo,
        String province,
        String firstName,
        String lastName,
        String personalTitle,
        String salutation,
        String companyName,
        String email,
        String addressType,
        Coordinates coordinates,
        List<PhoneNumber> phoneNumbers,
        Map<String, Object> customAttributes
) {

    /**
     * Returns a builder for constructing a {@code ConsumerAddress}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ConsumerAddress}.
     */
    public static final class Builder {

        private String street;
        private String houseNumber;
        private String city;
        private String postalCode;
        private String country;
        private String additionalAddressInfo;
        private String province;
        private String firstName;
        private String lastName;
        private String personalTitle;
        private String salutation;
        private String companyName;
        private String email;
        private String addressType;
        private Coordinates coordinates;
        private List<PhoneNumber> phoneNumbers;
        private Map<String, Object> customAttributes;

        private Builder() {}

        /**
         * Sets the street name.
         * @param street the street name
         * @return this builder
         */
        public Builder street(String street) {
            this.street = street;
            return this;
        }

        /**
         * Sets the house number.
         * @param houseNumber the house number
         * @return this builder
         */
        public Builder houseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        /**
         * Sets the city.
         * @param city the city name
         * @return this builder
         */
        public Builder city(String city) {
            this.city = city;
            return this;
        }

        /**
         * Sets the postal code.
         * @param postalCode the postal code
         * @return this builder
         */
        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        /**
         * Sets the ISO 3166-1 alpha-2 country code.
         * @param country the two-letter country code
         * @return this builder
         */
        public Builder country(String country) {
            this.country = country;
            return this;
        }

        /**
         * Sets supplementary address information.
         * @param additionalAddressInfo additional address info
         * @return this builder
         */
        public Builder additionalAddressInfo(String additionalAddressInfo) {
            this.additionalAddressInfo = additionalAddressInfo;
            return this;
        }

        /**
         * Sets the province or state.
         * @param province the province name
         * @return this builder
         */
        public Builder province(String province) {
            this.province = province;
            return this;
        }

        /**
         * Sets the first name of the recipient.
         * @param firstName the first name
         * @return this builder
         */
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        /**
         * Sets the last name of the recipient.
         * @param lastName the last name
         * @return this builder
         */
        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        /**
         * Sets the personal title of the recipient.
         * @param personalTitle the personal title (e.g. {@code Dr.})
         * @return this builder
         */
        public Builder personalTitle(String personalTitle) {
            this.personalTitle = personalTitle;
            return this;
        }

        /**
         * Sets the salutation.
         * @param salutation the salutation
         * @return this builder
         */
        public Builder salutation(String salutation) {
            this.salutation = salutation;
            return this;
        }

        /**
         * Sets the company name.
         * @param companyName the company name
         * @return this builder
         */
        public Builder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        /**
         * Sets the email address.
         * @param email the email address
         * @return this builder
         */
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets the address type.
         * @param addressType one of {@code POSTAL_ADDRESS}, {@code PARCEL_LOCKER},
         *                    or {@code INVOICE_ADDRESS}
         * @return this builder
         */
        public Builder addressType(String addressType) {
            this.addressType = addressType;
            return this;
        }

        /**
         * Sets the geo-coordinates for the address.
         * @param coordinates the coordinates
         * @return this builder
         */
        public Builder coordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        /**
         * Sets the list of phone numbers for the recipient.
         * @param phoneNumbers the phone numbers
         * @return this builder
         */
        public Builder phoneNumbers(List<PhoneNumber> phoneNumbers) {
            this.phoneNumbers = phoneNumbers;
            return this;
        }

        /**
         * Sets free-form custom attributes.
         * @param customAttributes the custom attributes map
         * @return this builder
         */
        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        /**
         * Builds a {@link ConsumerAddress}.
         *
         * @return a new instance.
         */
        public ConsumerAddress build() {
            return new ConsumerAddress(
                    street, houseNumber, city, postalCode, country,
                    additionalAddressInfo, province, firstName, lastName,
                    personalTitle, salutation, companyName, email, addressType,
                    coordinates, phoneNumbers, customAttributes);
        }
    }
}
