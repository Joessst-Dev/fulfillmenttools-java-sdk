package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * Payment information for creating an order.
 *
 * <p>Maps to the {@code OrderPaymentInfoForCreation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable; safe for concurrent use.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class OrderPaymentInfoForCreation {

    private final String currency;
    private final Map<String, String> methodLocalized;

    private OrderPaymentInfoForCreation(Builder builder) {
        this.currency = builder.currency;
        this.methodLocalized = builder.methodLocalized;
    }

    /**
     * Returns the currency in which the consumer paid (e.g. {@code EUR}).
     *
     * @return the currency, or null if not set
     */
    public String currency() { return currency; }

    /**
     * Returns localized payment method display names keyed by locale (e.g. {@code en_US}).
     *
     * @return the localized method names map, or null if not set
     */
    public Map<String, String> methodLocalized() { return methodLocalized; }

    /**
     * Creates a new builder for constructing an {@code OrderPaymentInfoForCreation}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link OrderPaymentInfoForCreation}.
     */
    public static final class Builder {
        /** Creates a new Builder. */
        public Builder() {}

        private String currency;
        private Map<String, String> methodLocalized;

        /**
         * Sets the currency in which the consumer paid.
         *
         * @param currency the currency code (e.g. {@code EUR})
         * @return this builder
         */
        public Builder currency(String currency) { this.currency = currency; return this; }

        /**
         * Sets localized payment method display names.
         *
         * @param methodLocalized the localized method names map
         * @return this builder
         */
        public Builder methodLocalized(Map<String, String> methodLocalized) { this.methodLocalized = methodLocalized; return this; }

        /**
         * Builds the {@link OrderPaymentInfoForCreation}.
         *
         * @return a new payment info
         */
        public OrderPaymentInfoForCreation build() { return new OrderPaymentInfoForCreation(this); }
    }
}
