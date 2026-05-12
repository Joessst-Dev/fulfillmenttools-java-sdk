package de.joesst.dev.fulfillmenttools.orders;

import java.util.Map;

public final class OrderForCreationConsumer {

    private final String email;
    private final String consumerId;
    private final Map<String, Object> customAttributes;

    private OrderForCreationConsumer(Builder builder) {
        this.email = builder.email;
        this.consumerId = builder.consumerId;
        this.customAttributes = builder.customAttributes;
    }

    public String email() { return email; }
    public String consumerId() { return consumerId; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String email;
        private String consumerId;
        private Map<String, Object> customAttributes;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder consumerId(String consumerId) {
            this.consumerId = consumerId;
            return this;
        }

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public OrderForCreationConsumer build() { return new OrderForCreationConsumer(this); }
    }
}
