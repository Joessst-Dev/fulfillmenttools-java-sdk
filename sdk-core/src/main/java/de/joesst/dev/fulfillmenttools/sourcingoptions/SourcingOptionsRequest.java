package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.Objects;

/**
 * Request to evaluate sourcing options for an order.
 *
 * <p>Defines the order to optimize, delivery constraints, and optimization hints for the
 * sourcing engine. Use the builder to construct requests with the required order field.
 */
public final class SourcingOptionsRequest {

    private final OrderForSourcingOptionsRequest order;
    private final SourcingOptionsRequestAdditionalInfo additionalInfo;
    private final OptimizationHints optimizationHints;
    private final ResourceInvestment resourceInvestment;

    private SourcingOptionsRequest(Builder builder) {
        this.order = Objects.requireNonNull(builder.order, "order must not be null");
        this.additionalInfo = builder.additionalInfo;
        this.optimizationHints = builder.optimizationHints;
        this.resourceInvestment = builder.resourceInvestment;
    }

    /**
     * Returns the order to evaluate.
     *
     * @return the order to evaluate
     */
    public OrderForSourcingOptionsRequest order() { return order; }

    /**
     * Returns optional additional configuration.
     *
     * @return optional additional configuration, or null if not set
     */
    public SourcingOptionsRequestAdditionalInfo additionalInfo() { return additionalInfo; }

    /**
     * Returns optional optimization hints.
     *
     * @return optional optimization hints, or null if not set
     */
    public OptimizationHints optimizationHints() { return optimizationHints; }

    /**
     * Returns optional resource investment level.
     *
     * @return optional resource investment level, or null if not set
     */
    public ResourceInvestment resourceInvestment() { return resourceInvestment; }

    /**
     * Returns a new builder for constructing {@code SourcingOptionsRequest} instances.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@code SourcingOptionsRequest}.
     *
     * <p>Allows fluent construction of sourcing options requests. The order field is required;
     * all other fields are optional.
     */
    public static final class Builder {

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        private OrderForSourcingOptionsRequest order;
        private SourcingOptionsRequestAdditionalInfo additionalInfo;
        private OptimizationHints optimizationHints;
        private ResourceInvestment resourceInvestment;

        /**
         * Sets the order to evaluate.
         *
         * @param order the order to evaluate (required)
         * @return this builder
         */
        public Builder order(OrderForSourcingOptionsRequest order) {
            this.order = order;
            return this;
        }

        /**
         * Sets optional additional configuration.
         *
         * @param additionalInfo optional additional configuration
         * @return this builder
         */
        public Builder additionalInfo(SourcingOptionsRequestAdditionalInfo additionalInfo) {
            this.additionalInfo = additionalInfo;
            return this;
        }

        /**
         * Sets optional hints to control the optimization engine.
         *
         * @param optimizationHints optional hints to control the optimization engine
         * @return this builder
         */
        public Builder optimizationHints(OptimizationHints optimizationHints) {
            this.optimizationHints = optimizationHints;
            return this;
        }

        /**
         * Sets optional resource investment level for computation.
         *
         * @param resourceInvestment optional resource investment level for computation
         * @return this builder
         */
        public Builder resourceInvestment(ResourceInvestment resourceInvestment) {
            this.resourceInvestment = resourceInvestment;
            return this;
        }

        /**
         * Builds the {@code SourcingOptionsRequest}.
         *
         * @return the constructed request
         * @throws NullPointerException if order has not been set
         */
        public SourcingOptionsRequest build() { return new SourcingOptionsRequest(this); }
    }
}
