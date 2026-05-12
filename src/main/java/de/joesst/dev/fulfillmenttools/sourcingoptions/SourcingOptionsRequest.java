package de.joesst.dev.fulfillmenttools.sourcingoptions;

import java.util.Objects;

public final class SourcingOptionsRequest {

    private final OrderForSourcingOptionsRequest order;
    private final Boolean includeListingCustomAttributes;

    private SourcingOptionsRequest(Builder builder) {
        this.order = Objects.requireNonNull(builder.order, "order must not be null");
        this.includeListingCustomAttributes = builder.includeListingCustomAttributes;
    }

    public OrderForSourcingOptionsRequest order() { return order; }
    public Boolean includeListingCustomAttributes() { return includeListingCustomAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private OrderForSourcingOptionsRequest order;
        private Boolean includeListingCustomAttributes;

        public Builder order(OrderForSourcingOptionsRequest order) {
            this.order = order;
            return this;
        }

        public Builder includeListingCustomAttributes(Boolean includeListingCustomAttributes) {
            this.includeListingCustomAttributes = includeListingCustomAttributes;
            return this;
        }

        public SourcingOptionsRequest build() { return new SourcingOptionsRequest(this); }
    }
}
