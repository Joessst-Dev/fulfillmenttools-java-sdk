package de.joesst.dev.fulfillmenttools.eventing;

/**
 * Request parameters for listing subscriptions.
 */
public final class SubscriptionListRequest {

    private final Integer size;
    private final String startAfterId;

    private SubscriptionListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
    }

    /**
     * Returns the maximum number of subscriptions to return.
     * @return the page size
     */
    public Integer size() { return size; }

    /**
     * Returns the ID after which to start the list.
     * @return the cursor for pagination
     */
    public String startAfterId() { return startAfterId; }

    /**
     * Creates a new builder from the current state.
     * @return a new builder with current values
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        return b;
    }

    /**
     * Creates a new builder for SubscriptionListRequest.
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for constructing SubscriptionListRequest instances.
     */
    public static final class Builder {

        /** Creates a new Builder. */
        public Builder() {}

        private Integer size;
        private String startAfterId;

        /**
         * Sets the maximum number of subscriptions to return.
         * @param size the page size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the ID after which to start the list.
         * @param startAfterId the cursor for pagination
         * @return this builder
         */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /**
         * Builds and returns a SubscriptionListRequest instance.
         * @return a new SubscriptionListRequest
         */
        public SubscriptionListRequest build() { return new SubscriptionListRequest(this); }
    }
}
