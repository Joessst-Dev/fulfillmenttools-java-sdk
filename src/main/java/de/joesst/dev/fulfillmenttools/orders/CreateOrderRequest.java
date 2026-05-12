package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CreateOrderRequest {

    private final Instant orderDate;
    private final List<OrderLineItemForCreation> orderLineItems;
    private final String tenantOrderId;
    private final OrderForCreationConsumer consumer;
    private final Map<String, Object> customAttributes;

    private CreateOrderRequest(Builder builder) {
        this.orderDate = Objects.requireNonNull(builder.orderDate, "orderDate must not be null");
        this.orderLineItems = Objects.requireNonNull(builder.orderLineItems, "orderLineItems must not be null");
        this.tenantOrderId = builder.tenantOrderId;
        this.consumer = builder.consumer;
        this.customAttributes = builder.customAttributes;
    }

    public Instant orderDate() { return orderDate; }
    public List<OrderLineItemForCreation> orderLineItems() { return orderLineItems; }
    public String tenantOrderId() { return tenantOrderId; }
    public OrderForCreationConsumer consumer() { return consumer; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Instant orderDate;
        private List<OrderLineItemForCreation> orderLineItems;
        private String tenantOrderId;
        private OrderForCreationConsumer consumer;
        private Map<String, Object> customAttributes;

        public Builder orderDate(Instant orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder orderLineItems(List<OrderLineItemForCreation> orderLineItems) {
            this.orderLineItems = orderLineItems;
            return this;
        }

        public Builder tenantOrderId(String tenantOrderId) {
            this.tenantOrderId = tenantOrderId;
            return this;
        }

        public Builder consumer(OrderForCreationConsumer consumer) {
            this.consumer = consumer;
            return this;
        }

        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public CreateOrderRequest build() { return new CreateOrderRequest(this); }
    }
}
