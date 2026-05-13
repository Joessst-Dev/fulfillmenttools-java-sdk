package de.joesst.dev.fulfillmenttools.routingplans;

import de.joesst.dev.fulfillmenttools.id.OrderId;

/**
 * Request parameters for listing routing plans with optional filtering and pagination.
 *
 * <p>Use the builder to construct a request with desired filters,
 * then pass it to {@code RoutingPlansClient.list()} or {@code listAsync()}.
 *
 * <p>All filter fields are optional. When {@code null}, they do not constrain results.
 */
public final class RoutingPlanListRequest {

    private final OrderId orderRef;

    /**
     * Creates a new request with the values from the builder.
     *
     * @param builder the builder
     */
    private RoutingPlanListRequest(Builder builder) {
        this.orderRef = builder.orderRef;
    }

    /**
     * Returns the order reference filter.
     *
     * @return the order reference filter, or null if not set
     */
    public OrderId orderRef() { return orderRef; }

    /**
     * Returns a new builder initialized with this request's current values.
     *
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.orderRef = this.orderRef;
        return b;
    }

    /**
     * Returns a new builder for constructing {@code RoutingPlanListRequest} instances.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@code RoutingPlanListRequest}.
     *
     * <p>Allows fluent construction of routing plan list requests with optional filters.
     * All filters are optional; {@code null} filters are not applied to the query.
     */
    public static final class Builder {

        private OrderId orderRef;

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        /**
         * Sets the order reference filter.
         *
         * @param orderRef order reference to filter by
         * @return this builder
         */
        public Builder orderRef(OrderId orderRef) { this.orderRef = orderRef; return this; }

        /**
         * Builds the {@code RoutingPlanListRequest}.
         *
         * @return the constructed request
         */
        public RoutingPlanListRequest build() { return new RoutingPlanListRequest(this); }
    }
}
