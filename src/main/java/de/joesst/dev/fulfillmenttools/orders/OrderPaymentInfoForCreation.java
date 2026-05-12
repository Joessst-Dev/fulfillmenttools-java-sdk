package de.joesst.dev.fulfillmenttools.orders;

import java.util.Map;

public final class OrderPaymentInfoForCreation {

    private final String currency;
    private final Map<String, Object> methodLocalized;

    private OrderPaymentInfoForCreation(Builder builder) {
        this.currency = builder.currency;
        this.methodLocalized = builder.methodLocalized;
    }

    public String currency() { return currency; }
    public Map<String, Object> methodLocalized() { return methodLocalized; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String currency;
        private Map<String, Object> methodLocalized;

        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder methodLocalized(Map<String, Object> methodLocalized) { this.methodLocalized = methodLocalized; return this; }

        public OrderPaymentInfoForCreation build() { return new OrderPaymentInfoForCreation(this); }
    }
}
