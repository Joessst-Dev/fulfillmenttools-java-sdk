package de.joesst.dev.fulfillmenttools.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A monetary amount with currency.
 *
 * <p>Maps to the {@code Money} schema in the fulfillmenttools OpenAPI specification.
 *
 * @param value         the monetary value
 * @param currency      the ISO 4217 currency code (e.g. {@code EUR})
 * @param decimalPlaces the number of decimal places for this currency
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Money(
        Double value,
        String currency,
        Double decimalPlaces
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Double value;
        private String currency;
        private Double decimalPlaces;

        public Builder value(Double value) { this.value = value; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder decimalPlaces(Double decimalPlaces) { this.decimalPlaces = decimalPlaces; return this; }

        public Money build() {
            return new Money(value, currency, decimalPlaces);
        }
    }
}
