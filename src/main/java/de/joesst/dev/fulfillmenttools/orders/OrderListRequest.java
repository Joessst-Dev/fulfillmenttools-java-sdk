package de.joesst.dev.fulfillmenttools.orders;

public final class OrderListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String tenantOrderId;
    private final String consumerId;

    private OrderListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.tenantOrderId = builder.tenantOrderId;
        this.consumerId = builder.consumerId;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public String tenantOrderId() { return tenantOrderId; }
    public String consumerId() { return consumerId; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.tenantOrderId = this.tenantOrderId;
        b.consumerId = this.consumerId;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Integer size;
        private String startAfterId;
        private String tenantOrderId;
        private String consumerId;

        public Builder size(int size) { this.size = size; return this; }
        public Builder startAfterId(String cursor) { this.startAfterId = cursor; return this; }
        public Builder tenantOrderId(String tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }
        public Builder consumerId(String consumerId) { this.consumerId = consumerId; return this; }
        public OrderListRequest build() { return new OrderListRequest(this); }
    }
}
