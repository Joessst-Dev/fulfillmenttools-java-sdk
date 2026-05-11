package de.joesst.dev.fulfillmenttools.orders;

public final class OrderListRequest {

    private final Integer size;
    private final String startAfterId;

    private OrderListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Integer size;
        private String startAfterId;

        public Builder size(int size) { this.size = size; return this; }
        public Builder startAfterId(String cursor) { this.startAfterId = cursor; return this; }
        public OrderListRequest build() { return new OrderListRequest(this); }
    }
}
