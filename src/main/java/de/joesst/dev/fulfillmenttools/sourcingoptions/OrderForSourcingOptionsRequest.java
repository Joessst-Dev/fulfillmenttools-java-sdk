package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.List;
import java.util.Objects;

public final class OrderForSourcingOptionsRequest {

    private final List<SourcingOrderLineItem> orderLineItems;
    private final String tenantOrderId;

    private OrderForSourcingOptionsRequest(Builder builder) {
        this.orderLineItems = Objects.requireNonNull(builder.orderLineItems, "orderLineItems must not be null");
        this.tenantOrderId = builder.tenantOrderId;
    }

    public List<SourcingOrderLineItem> orderLineItems() { return orderLineItems; }
    public String tenantOrderId() { return tenantOrderId; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private List<SourcingOrderLineItem> orderLineItems;
        private String tenantOrderId;

        public Builder orderLineItems(List<SourcingOrderLineItem> orderLineItems) {
            this.orderLineItems = orderLineItems;
            return this;
        }

        public Builder tenantOrderId(String tenantOrderId) {
            this.tenantOrderId = tenantOrderId;
            return this;
        }

        public OrderForSourcingOptionsRequest build() { return new OrderForSourcingOptionsRequest(this); }
    }
}
