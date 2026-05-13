package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.util.List;

/**
 * Non-delivery day configuration for a country, optionally refined per province.
 * Corresponds to {@code NonDeliveryDaysPerCountryAndProvince} in the OpenAPI spec.
 *
 * @param country                     ISO 3166-1 alpha-2 country code
 * @param nonDeliveryDays             explicit non-delivery dates that apply country-wide
 * @param nonDeliveryDaysPerProvince  province-level overrides
 * @param recurringNonDeliveryWeekdays weekdays on which delivery never occurs in this country
 */
public record NonDeliveryDaysPerCountry(
        String country,
        List<NonDeliveryDay> nonDeliveryDays,
        List<NonDeliveryDaysPerProvince> nonDeliveryDaysPerProvince,
        List<WeekDay> recurringNonDeliveryWeekdays
) {

    /**
     * Returns a builder for constructing a {@link NonDeliveryDaysPerCountry}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link NonDeliveryDaysPerCountry}.
     */
    public static final class Builder {

        private String country;
        private List<NonDeliveryDay> nonDeliveryDays;
        private List<NonDeliveryDaysPerProvince> nonDeliveryDaysPerProvince;
        private List<WeekDay> recurringNonDeliveryWeekdays;

        private Builder() {}

        /**
         * @param country ISO 3166-1 alpha-2 country code
         * @return this builder
         */
        public Builder country(String country) {
            this.country = country;
            return this;
        }

        /**
         * @param nonDeliveryDays explicit non-delivery dates that apply country-wide
         * @return this builder
         */
        public Builder nonDeliveryDays(List<NonDeliveryDay> nonDeliveryDays) {
            this.nonDeliveryDays = nonDeliveryDays;
            return this;
        }

        /**
         * @param nonDeliveryDaysPerProvince province-level overrides
         * @return this builder
         */
        public Builder nonDeliveryDaysPerProvince(List<NonDeliveryDaysPerProvince> nonDeliveryDaysPerProvince) {
            this.nonDeliveryDaysPerProvince = nonDeliveryDaysPerProvince;
            return this;
        }

        /**
         * @param recurringNonDeliveryWeekdays weekdays on which delivery never occurs in this country
         * @return this builder
         */
        public Builder recurringNonDeliveryWeekdays(List<WeekDay> recurringNonDeliveryWeekdays) {
            this.recurringNonDeliveryWeekdays = recurringNonDeliveryWeekdays;
            return this;
        }

        /**
         * Builds a {@link NonDeliveryDaysPerCountry}.
         *
         * @return a new instance
         */
        public NonDeliveryDaysPerCountry build() {
            return new NonDeliveryDaysPerCountry(
                    country, nonDeliveryDays, nonDeliveryDaysPerProvince, recurringNonDeliveryWeekdays);
        }
    }
}
