package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A monetary delivery cost, optionally refined by decimal precision and a cost coefficient.
 *
 * @param value                    the cost amount as a scaled integer
 * @param currency                 ISO 4217 currency code (e.g. "EUR")
 * @param decimalPlaces            optional number of decimal places; {@code null} defaults to the currency's standard
 * @param deliveryCostCoefficient  optional per-unit cost coefficient; {@code null} if not applicable
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DeliveryCost(
        Integer value,
        String currency,
        Integer decimalPlaces,
        DeliveryCostCoefficient deliveryCostCoefficient
) {

    /**
     * Returns a builder for constructing a {@link DeliveryCost}.
     *
     * @return a new {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link DeliveryCost}.
     */
    public static final class Builder {

        private Integer value;
        private String currency;
        private Integer decimalPlaces;
        private DeliveryCostCoefficient deliveryCostCoefficient;

        private Builder() {}

        /**
         * @param value the cost amount as a scaled integer
         * @return this builder
         */
        public Builder value(Integer value) {
            this.value = value;
            return this;
        }

        /**
         * @param currency ISO 4217 currency code (e.g. "EUR")
         * @return this builder
         */
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        /**
         * @param decimalPlaces optional number of decimal places
         * @return this builder
         */
        public Builder decimalPlaces(Integer decimalPlaces) {
            this.decimalPlaces = decimalPlaces;
            return this;
        }

        /**
         * @param deliveryCostCoefficient optional per-unit cost coefficient
         * @return this builder
         */
        public Builder deliveryCostCoefficient(DeliveryCostCoefficient deliveryCostCoefficient) {
            this.deliveryCostCoefficient = deliveryCostCoefficient;
            return this;
        }

        /**
         * Builds a {@link DeliveryCost}.
         *
         * @return a new instance
         */
        public DeliveryCost build() {
            return new DeliveryCost(value, currency, decimalPlaces, deliveryCostCoefficient);
        }
    }
}
