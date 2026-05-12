package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class UpdateOrderRequest {

    private final Integer version;
    private final String comment;
    private final OrderForCreationConsumer consumer;
    private final Map<String, Object> customAttributes;
    private final List<OrderLineItemForUpdate> orderLineItems;
    private final Instant preferredHandlingTime;

    private UpdateOrderRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.comment = builder.comment;
        this.consumer = builder.consumer;
        this.customAttributes = builder.customAttributes;
        this.orderLineItems = builder.orderLineItems;
        this.preferredHandlingTime = builder.preferredHandlingTime;
    }

    public Integer version() { return version; }
    public String comment() { return comment; }
    public OrderForCreationConsumer consumer() { return consumer; }
    public Map<String, Object> customAttributes() { return customAttributes; }
    public List<OrderLineItemForUpdate> orderLineItems() { return orderLineItems; }
    public Instant preferredHandlingTime() { return preferredHandlingTime; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer version;
        private String comment;
        private OrderForCreationConsumer consumer;
        private Map<String, Object> customAttributes;
        private List<OrderLineItemForUpdate> orderLineItems;
        private Instant preferredHandlingTime;

        public Builder version(Integer version) { this.version = version; return this; }
        public Builder comment(String comment) { this.comment = comment; return this; }
        public Builder consumer(OrderForCreationConsumer consumer) { this.consumer = consumer; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }
        public Builder orderLineItems(List<OrderLineItemForUpdate> orderLineItems) { this.orderLineItems = orderLineItems; return this; }
        public Builder preferredHandlingTime(Instant preferredHandlingTime) { this.preferredHandlingTime = preferredHandlingTime; return this; }

        public UpdateOrderRequest build() { return new UpdateOrderRequest(this); }
    }
}
