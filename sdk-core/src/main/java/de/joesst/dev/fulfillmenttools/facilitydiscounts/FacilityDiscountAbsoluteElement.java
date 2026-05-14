package de.joesst.dev.fulfillmenttools.facilitydiscounts;

/**
 * Represents an absolute discount element with a specific currency and precision.
 *
 * @param value the discount amount
 * @param currency the currency code (e.g., USD, EUR)
 * @param decimalPlaces the number of decimal places for the currency
 */
public record FacilityDiscountAbsoluteElement(Integer value, String currency, Integer decimalPlaces) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Integer value;
        private String currency;
        private Integer decimalPlaces;

        public Builder value(Integer value) {
            this.value = value;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder decimalPlaces(Integer decimalPlaces) {
            this.decimalPlaces = decimalPlaces;
            return this;
        }

        public FacilityDiscountAbsoluteElement build() {
            return new FacilityDiscountAbsoluteElement(value, currency, decimalPlaces);
        }
    }
}
