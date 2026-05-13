package de.joesst.dev.fulfillmenttools.orders;

/**
 * Request to list orders with optional filtering and pagination.
 */
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

    /**
     * Returns the page size.
     *
     * @return the size, or null if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the cursor for pagination.
     *
     * @return the start after ID, or null if not set
     */
    public String startAfterId() { return startAfterId; }

    /**
     * Returns the tenant order ID filter.
     *
     * @return the tenant order ID, or null if not set
     */
    public String tenantOrderId() { return tenantOrderId; }

    /**
     * Returns the consumer ID filter.
     *
     * @return the consumer ID, or null if not set
     */
    public String consumerId() { return consumerId; }

    /**
     * Creates a new builder with the same state as this request.
     *
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.tenantOrderId = this.tenantOrderId;
        b.consumerId = this.consumerId;
        return b;
    }

    /**
     * Creates a new builder for constructing an {@code OrderListRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link OrderListRequest}.
     */
    public static final class Builder {
        /** Creates a new Builder. */
        public Builder() {}

        private Integer size;
        private String startAfterId;
        private String tenantOrderId;
        private String consumerId;

        /**
         * Sets the page size.
         *
         * @param size the size
         * @return this builder
         */
        public Builder size(int size) { this.size = size; return this; }

        /**
         * Sets the pagination cursor.
         *
         * @param cursor the cursor
         * @return this builder
         */
        public Builder startAfterId(String cursor) { this.startAfterId = cursor; return this; }

        /**
         * Sets the tenant order ID filter.
         *
         * @param tenantOrderId the tenant order ID
         * @return this builder
         */
        public Builder tenantOrderId(String tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }

        /**
         * Sets the consumer ID filter.
         *
         * @param consumerId the consumer ID
         * @return this builder
         */
        public Builder consumerId(String consumerId) { this.consumerId = consumerId; return this; }

        /**
         * Builds the {@link OrderListRequest}.
         *
         * @return a new request
         */
        public OrderListRequest build() { return new OrderListRequest(this); }
    }
}
