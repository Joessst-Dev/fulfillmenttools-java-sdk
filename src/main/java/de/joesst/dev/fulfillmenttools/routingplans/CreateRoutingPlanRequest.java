package de.joesst.dev.fulfillmenttools.routingplans;

import java.util.Objects;

/**
 * Request for creating a new routing plan.
 *
 * <p>A routing plan defines the decision-making record for fulfilling a customer order.
 * Use the builder to construct a creation request with a required name.
 */
public final class CreateRoutingPlanRequest {

    private final String name;

    private CreateRoutingPlanRequest(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name must not be null");
    }

    /**
     * Returns the routing plan name.
     * @return the routing plan name
     */
    public String name() { return name; }

    /**
     * Returns a new builder for constructing {@code CreateRoutingPlanRequest} instances.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@code CreateRoutingPlanRequest}.
     *
     * <p>The name field is required.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private String name;

        /**
         * Sets the name for the routing plan.
         * @param name the routing plan name (required)
         * @return this builder
         */
        public Builder name(String name) { this.name = name; return this; }

        /**
         * Builds the {@code CreateRoutingPlanRequest}.
         *
         * @return the constructed request
         * @throws NullPointerException if name has not been set
         */
        public CreateRoutingPlanRequest build() { return new CreateRoutingPlanRequest(this); }
    }
}
