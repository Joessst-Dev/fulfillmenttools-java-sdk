package de.joesst.dev.fulfillmenttools.returns;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Refund information attached to a returned line item.
 *
 * <p>Maps to the {@code ReturnedLineItemRefund} schema in the fulfillmenttools OpenAPI spec.
 * Either {@code price} or {@code percent} must be set, not both.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param status  The refund status: {@code OPEN}, {@code IN_PROGRESS}, or {@code CLOSED}.
 *                Required.
 * @param price   An absolute refund price. Mutually exclusive with {@code percent}.
 * @param percent A percentage of the line item price to refund (0.0–100.0).
 *                Mutually exclusive with {@code price}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReturnedLineItemRefund(
        String status,
        ReturnedLineItemRefundPrice price,
        Double percent
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String status;
        private ReturnedLineItemRefundPrice price;
        private Double percent;

        private Builder() {}

        public Builder status(String status) { this.status = status; return this; }
        public Builder price(ReturnedLineItemRefundPrice price) { this.price = price; return this; }
        public Builder percent(Double percent) { this.percent = percent; return this; }

        public ReturnedLineItemRefund build() {
            return new ReturnedLineItemRefund(status, price, percent);
        }
    }
}
