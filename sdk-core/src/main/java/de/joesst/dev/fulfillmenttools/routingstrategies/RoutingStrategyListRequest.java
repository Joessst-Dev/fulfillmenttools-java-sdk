package de.joesst.dev.fulfillmenttools.routingstrategies;

/**
 * Request parameters for listing routing strategies with optional pagination.
 *
 * <p>Use the builder to construct a request with desired pagination settings,
 * then pass it to {@code RoutingStrategiesClient.list()} or {@code listAsync()}.
 */
public final class RoutingStrategyListRequest {

    private final Integer size;
    private final String startAfterId;

    /**
     * Creates a new request with the values from the builder.
     *
     * @param builder the builder
     */
    private RoutingStrategyListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
    }

    /**
     * Returns the maximum number of results to return.
     *
     * @return the page size, or null if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the pagination cursor ID.
     *
     * @return the cursor ID, or null if not set
     */
    public String startAfterId() { return startAfterId; }

    /**
     * Returns a new builder initialized with this request's current values.
     *
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        return b;
    }

    /**
     * Returns a new builder for constructing {@code RoutingStrategyListRequest} instances.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@code RoutingStrategyListRequest}.
     *
     * <p>Allows fluent construction of routing strategy list requests with optional pagination parameters.
     */
    public static final class Builder {

        private Integer size;
        private String startAfterId;

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        /**
         * Sets the maximum number of results to return.
         *
         * @param size the page size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the pagination cursor ID.
         *
         * @param startAfterId the cursor ID
         * @return this builder
         */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /**
         * Builds the {@code RoutingStrategyListRequest}.
         *
         * @return the constructed request
         */
        public RoutingStrategyListRequest build() { return new RoutingStrategyListRequest(this); }
    }
}
