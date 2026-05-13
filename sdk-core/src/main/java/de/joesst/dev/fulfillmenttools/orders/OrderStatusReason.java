package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A reason explaining why an order is in a particular status.
 *
 * <p>Maps to the {@code OrderStatusReason} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param reason The human-readable reason text.
 * @param status The order status this reason is associated with.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderStatusReason(
        String reason,
        String status
) {

    /**
     * Returns a builder for constructing an {@code OrderStatusReason}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link OrderStatusReason}.
     */
    public static final class Builder {

        private String reason;
        private String status;

        private Builder() {}

        /**
         * Sets the human-readable reason text.
         * @param reason the reason text
         * @return this builder
         */
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        /**
         * Sets the order status this reason is associated with.
         * @param status the order status
         * @return this builder
         */
        public Builder status(String status) {
            this.status = status;
            return this;
        }

        /**
         * Builds an {@link OrderStatusReason}.
         *
         * @return a new instance.
         */
        public OrderStatusReason build() {
            return new OrderStatusReason(reason, status);
        }
    }
}
