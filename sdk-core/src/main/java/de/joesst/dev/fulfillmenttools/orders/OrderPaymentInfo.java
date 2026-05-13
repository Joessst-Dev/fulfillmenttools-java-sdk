package de.joesst.dev.fulfillmenttools.orders;

import java.util.Map;

/**
 * Payment information on a read-side order.
 *
 * <p>Maps to the {@code OrderPaymentInfo} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param currency       The currency in which the consumer paid (e.g. {@code EUR}).
 * @param method         The localized payment method name (resolved from {@code methodLocalized}).
 * @param methodLocalized Localized display names for the payment method, keyed by locale.
 */
public record OrderPaymentInfo(
        String currency,
        String method,
        Map<String, String> methodLocalized
) {

    /**
     * Returns a builder for constructing an {@code OrderPaymentInfo}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link OrderPaymentInfo}.
     */
    public static final class Builder {

        private String currency;
        private String method;
        private Map<String, String> methodLocalized;

        private Builder() {}

        /**
         * Sets the currency in which the consumer paid.
         * @param currency the currency code (e.g. {@code EUR})
         * @return this builder
         */
        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        /**
         * Sets the localized payment method name.
         * @param method the payment method name
         * @return this builder
         */
        public Builder method(String method) {
            this.method = method;
            return this;
        }

        /**
         * Sets the localized display names for the payment method, keyed by locale.
         * @param methodLocalized map of locale to localized method name
         * @return this builder
         */
        public Builder methodLocalized(Map<String, String> methodLocalized) {
            this.methodLocalized = methodLocalized;
            return this;
        }

        /**
         * Builds an {@link OrderPaymentInfo}.
         *
         * @return a new instance.
         */
        public OrderPaymentInfo build() {
            return new OrderPaymentInfo(currency, method, methodLocalized);
        }
    }
}
