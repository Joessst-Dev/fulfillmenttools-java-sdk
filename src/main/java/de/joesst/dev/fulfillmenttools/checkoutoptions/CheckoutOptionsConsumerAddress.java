package de.joesst.dev.fulfillmenttools.checkoutoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Consumer delivery address supplied as part of a checkout options evaluation request.
 *
 * <p>Maps to the {@code CheckoutOptionsConsumerAddress} schema in the fulfillmenttools
 * OpenAPI spec.  {@code country} is the only required field; all others are optional
 * and omitted from the JSON payload when {@code null}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param country     ISO 3166-1 alpha-2 country code (required).
 * @param city        City name.
 * @param houseNumber House or building number.
 * @param postalCode  Postal / ZIP code.
 * @param province    Province, state, or region.
 * @param street      Street name.
 * @param addressType One of {@code POSTAL_ADDRESS}, {@code PARCEL_LOCKER}, or
 *                    {@code INVOICE_ADDRESS}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CheckoutOptionsConsumerAddress(
        String country,
        String city,
        String houseNumber,
        String postalCode,
        String province,
        String street,
        String addressType
) {

    /**
     * Creates a minimal consumer address with only the required {@code country} field.
     *
     * @param country ISO 3166-1 alpha-2 country code; must not be {@code null}.
     * @return a new {@code CheckoutOptionsConsumerAddress} with all optional fields {@code null}.
     * @throws NullPointerException if {@code country} is {@code null}.
     */
    public static CheckoutOptionsConsumerAddress ofCountry(String country) {
        if (country == null) {
            throw new NullPointerException("country must not be null");
        }
        return new CheckoutOptionsConsumerAddress(country, null, null, null, null, null, null);
    }

    /**
     * Returns a builder for constructing a {@code CheckoutOptionsConsumerAddress}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link CheckoutOptionsConsumerAddress}.
     */
    public static final class Builder {

        private String country;
        private String city;
        private String houseNumber;
        private String postalCode;
        private String province;
        private String street;
        private String addressType;

        private Builder() {}

        /** @param country ISO 3166-1 alpha-2 country code (required). */
        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder houseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder province(String province) {
            this.province = province;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        /**
         * @param addressType one of {@code POSTAL_ADDRESS}, {@code PARCEL_LOCKER},
         *                    or {@code INVOICE_ADDRESS}.
         */
        public Builder addressType(String addressType) {
            this.addressType = addressType;
            return this;
        }

        /**
         * Builds a {@link CheckoutOptionsConsumerAddress}.
         *
         * @return a new instance.
         * @throws NullPointerException if {@code country} has not been set.
         */
        public CheckoutOptionsConsumerAddress build() {
            if (country == null) {
                throw new NullPointerException("country must not be null");
            }
            return new CheckoutOptionsConsumerAddress(
                    country, city, houseNumber, postalCode, province, street, addressType);
        }
    }
}
