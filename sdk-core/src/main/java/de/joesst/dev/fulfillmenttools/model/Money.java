package de.joesst.dev.fulfillmenttools.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A monetary amount stored as a whole number plus a decimal-places indicator.
 *
 * <p>{@code value} is always an integer-like number (e.g. {@code 499}); {@code decimalPlaces}
 * tells you how many digits to shift right to get the real amount (e.g. {@code 2} → divide by
 * 100 → {@code 4.99}). This design avoids floating-point conversion errors.
 *
 * <p>Maps to the {@code Money} schema in the fulfillmenttools OpenAPI specification.
 *
 * @param value         the amount as a whole number (e.g. {@code 499} represents €4.99)
 * @param currency      the ISO 4217 currency code (e.g. {@code EUR})
 * @param decimalPlaces how many decimal places to shift {@code value} right to obtain the real
 *                      amount; set by whoever creates the value, not derived from the currency
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Money(
        Double value,
        String currency,
        Double decimalPlaces
) {

    /**
     * Returns the value scaled to its decimal representation.
     * Divides {@code value} by 10^{@code decimalPlaces} (e.g. 499.0 with 2 decimal places → 4.99).
     * Returns {@code value} unchanged when {@code decimalPlaces} is null or zero.
     */
    public Double toAmount() {
        if (value == null) return null;
        if (decimalPlaces == null || Double.compare(decimalPlaces, 0.0) == 0) return value;
        return value / Math.pow(10, decimalPlaces);
    }

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
