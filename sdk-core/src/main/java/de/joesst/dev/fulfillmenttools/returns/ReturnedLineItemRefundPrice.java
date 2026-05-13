package de.joesst.dev.fulfillmenttools.returns;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * An absolute price amount for a return line item refund.
 *
 * <p>Maps to the {@code ReturnedLineItemRefundPrice} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param value    The refund amount; between {@code 0.0} and the line item price. Required.
 * @param currency ISO 4217 currency code (e.g. {@code EUR}). Required.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReturnedLineItemRefundPrice(
        Double value,
        String currency
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Double value;
        private String currency;

        private Builder() {}

        public Builder value(Double value) { this.value = value; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }

        public ReturnedLineItemRefundPrice build() {
            return new ReturnedLineItemRefundPrice(value, currency);
        }
    }
}
