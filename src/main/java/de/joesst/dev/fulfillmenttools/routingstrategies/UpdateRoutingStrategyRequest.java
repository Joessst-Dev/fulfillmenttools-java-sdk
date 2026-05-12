package de.joesst.dev.fulfillmenttools.routingstrategies;

public final class UpdateRoutingStrategyRequest {

    private final String name;
    private final String status;

    private UpdateRoutingStrategyRequest(Builder builder) {
        this.name = builder.name;
        this.status = builder.status;
    }

    public String name() { return name; }
    public String status() { return status; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String name;
        private String status;

        public Builder name(String name) { this.name = name; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public UpdateRoutingStrategyRequest build() { return new UpdateRoutingStrategyRequest(this); }
    }
}
