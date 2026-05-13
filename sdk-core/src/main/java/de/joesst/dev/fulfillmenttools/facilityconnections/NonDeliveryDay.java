package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

/**
 * A single non-delivery day entry.
 *
 * @param nonDeliveryDay  the calendar date on which no delivery takes place
 * @param nonDeliveryType optional type of non-delivery event; defaults to {@code SINGLE} when absent
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record NonDeliveryDay(LocalDate nonDeliveryDay, String nonDeliveryType) {

    /**
     * Returns a builder for constructing a {@link NonDeliveryDay}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link NonDeliveryDay}.
     */
    public static final class Builder {

        private LocalDate nonDeliveryDay;
        private String nonDeliveryType;

        private Builder() {}

        /**
         * @param nonDeliveryDay the calendar date on which no delivery takes place
         * @return this builder
         */
        public Builder nonDeliveryDay(LocalDate nonDeliveryDay) {
            this.nonDeliveryDay = nonDeliveryDay;
            return this;
        }

        /**
         * @param nonDeliveryType optional type of non-delivery event
         * @return this builder
         */
        public Builder nonDeliveryType(String nonDeliveryType) {
            this.nonDeliveryType = nonDeliveryType;
            return this;
        }

        /**
         * Builds a {@link NonDeliveryDay}.
         *
         * @return a new instance
         */
        public NonDeliveryDay build() {
            return new NonDeliveryDay(nonDeliveryDay, nonDeliveryType);
        }
    }
}
