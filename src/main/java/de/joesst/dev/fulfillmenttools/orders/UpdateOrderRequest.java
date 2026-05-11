package de.joesst.dev.fulfillmenttools.orders;

public final class UpdateOrderRequest {

    private final String tenantOrderId;
    private final String status;

    private UpdateOrderRequest(Builder builder) {
        this.tenantOrderId = builder.tenantOrderId;
        this.status = builder.status;
    }

    public String tenantOrderId() { return tenantOrderId; }
    public String status() { return status; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String tenantOrderId;
        private String status;

        public Builder tenantOrderId(String tenantOrderId) {
            this.tenantOrderId = tenantOrderId;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public UpdateOrderRequest build() { return new UpdateOrderRequest(this); }
    }
}
