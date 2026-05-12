package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.Objects;

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

    public OrderForSourcingOptionsRequest order() { return order; }
    public SourcingOptionsRequestAdditionalInfo additionalInfo() { return additionalInfo; }
    public OptimizationHints optimizationHints() { return optimizationHints; }
    public ResourceInvestment resourceInvestment() { return resourceInvestment; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private OrderForSourcingOptionsRequest order;
        private SourcingOptionsRequestAdditionalInfo additionalInfo;
        private OptimizationHints optimizationHints;
        private ResourceInvestment resourceInvestment;

        public Builder order(OrderForSourcingOptionsRequest order) {
            this.order = order;
            return this;
        }

        public Builder additionalInfo(SourcingOptionsRequestAdditionalInfo additionalInfo) {
            this.additionalInfo = additionalInfo;
            return this;
        }

        public Builder optimizationHints(OptimizationHints optimizationHints) {
            this.optimizationHints = optimizationHints;
            return this;
        }

        public Builder resourceInvestment(ResourceInvestment resourceInvestment) {
            this.resourceInvestment = resourceInvestment;
            return this;
        }

        public SourcingOptionsRequest build() { return new SourcingOptionsRequest(this); }
    }
}
