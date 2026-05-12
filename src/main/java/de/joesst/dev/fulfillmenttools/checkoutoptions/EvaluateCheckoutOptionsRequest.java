package de.joesst.dev.fulfillmenttools.checkoutoptions;

import java.util.Objects;

public final class EvaluateCheckoutOptionsRequest {

    private final String orderId;

    private EvaluateCheckoutOptionsRequest(Builder builder) {
        this.orderId = Objects.requireNonNull(builder.orderId, "orderId must not be null");
    }

    public String orderId() { return orderId; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String orderId;

        public Builder orderId(String orderId) { this.orderId = orderId; return this; }

        public EvaluateCheckoutOptionsRequest build() { return new EvaluateCheckoutOptionsRequest(this); }
    }
}
