package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.util.List;

/**
 * Non-delivery day configuration for a specific province within a country.
 *
 * @param province                    province or state code
 * @param nonDeliveryDays             explicit non-delivery dates for this province
 * @param recurringNonDeliveryWeekdays weekdays on which delivery never occurs in this province
 */
public record NonDeliveryDaysPerProvince(
        String province,
        List<NonDeliveryDay> nonDeliveryDays,
        List<WeekDay> recurringNonDeliveryWeekdays
) {

    /**
     * Returns a builder for constructing a {@link NonDeliveryDaysPerProvince}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link NonDeliveryDaysPerProvince}.
     */
    public static final class Builder {

        private String province;
        private List<NonDeliveryDay> nonDeliveryDays;
        private List<WeekDay> recurringNonDeliveryWeekdays;

        private Builder() {}

        /**
         * @param province province or state code
         * @return this builder
         */
        public Builder province(String province) {
            this.province = province;
            return this;
        }

        /**
         * @param nonDeliveryDays explicit non-delivery dates for this province
         * @return this builder
         */
        public Builder nonDeliveryDays(List<NonDeliveryDay> nonDeliveryDays) {
            this.nonDeliveryDays = nonDeliveryDays;
            return this;
        }

        /**
         * @param recurringNonDeliveryWeekdays weekdays on which delivery never occurs in this province
         * @return this builder
         */
        public Builder recurringNonDeliveryWeekdays(List<WeekDay> recurringNonDeliveryWeekdays) {
            this.recurringNonDeliveryWeekdays = recurringNonDeliveryWeekdays;
            return this;
        }

        /**
         * Builds a {@link NonDeliveryDaysPerProvince}.
         *
         * @return a new instance
         */
        public NonDeliveryDaysPerProvince build() {
            return new NonDeliveryDaysPerProvince(province, nonDeliveryDays, recurringNonDeliveryWeekdays);
        }
    }
}
