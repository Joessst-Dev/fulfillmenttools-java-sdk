package de.joesst.dev.fulfillmenttools.routingplans;

import java.util.Objects;

public final class CreateRoutingPlanRequest {

    private final String name;

    private CreateRoutingPlanRequest(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name must not be null");
    }

    public String name() { return name; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String name;

        public Builder name(String name) { this.name = name; return this; }

        public CreateRoutingPlanRequest build() { return new CreateRoutingPlanRequest(this); }
    }
}
