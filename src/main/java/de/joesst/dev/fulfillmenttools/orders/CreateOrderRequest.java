package de.joesst.dev.fulfillmenttools.orders;

import java.time.Instant;
import java.util.Objects;

public final class CreateOrderRequest {

    private final String tenantOrderId;
    private final Instant orderDate;

    private CreateOrderRequest(Builder builder) {
        this.tenantOrderId = Objects.requireNonNull(builder.tenantOrderId, "tenantOrderId must not be null");
        this.orderDate = builder.orderDate;
    }

    public String tenantOrderId() { return tenantOrderId; }
    public Instant orderDate() { return orderDate; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String tenantOrderId;
        private Instant orderDate;

        public Builder tenantOrderId(String tenantOrderId) {
            this.tenantOrderId = tenantOrderId;
            return this;
        }

        public Builder orderDate(Instant orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public CreateOrderRequest build() { return new CreateOrderRequest(this); }
    }
}
