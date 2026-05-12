package de.joesst.dev.fulfillmenttools.routingplans;

public final class RoutingPlanListRequest {

    private final String orderRef;

    private RoutingPlanListRequest(Builder builder) {
        this.orderRef = builder.orderRef;
    }

    public String orderRef() { return orderRef; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.orderRef = this.orderRef;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String orderRef;

        public Builder orderRef(String orderRef) { this.orderRef = orderRef; return this; }

        public RoutingPlanListRequest build() { return new RoutingPlanListRequest(this); }
    }
}
