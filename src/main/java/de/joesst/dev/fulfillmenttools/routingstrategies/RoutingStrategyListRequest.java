package de.joesst.dev.fulfillmenttools.routingstrategies;

public final class RoutingStrategyListRequest {

    private final Integer size;
    private final String startAfterId;

    private RoutingStrategyListRequest(Builder builder) {
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

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        public RoutingStrategyListRequest build() { return new RoutingStrategyListRequest(this); }
    }
}
