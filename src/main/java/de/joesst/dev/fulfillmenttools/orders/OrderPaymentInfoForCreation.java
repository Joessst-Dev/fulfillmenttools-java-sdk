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

    /** The currency in which the consumer paid (e.g. {@code EUR}). */
    public String currency() { return currency; }
    /** Localized payment method display names keyed by locale (e.g. {@code en_US}). */
    public Map<String, String> methodLocalized() { return methodLocalized; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String currency;
        private Map<String, String> methodLocalized;

        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder methodLocalized(Map<String, String> methodLocalized) { this.methodLocalized = methodLocalized; return this; }

        public OrderPaymentInfoForCreation build() { return new OrderPaymentInfoForCreation(this); }
    }
}
