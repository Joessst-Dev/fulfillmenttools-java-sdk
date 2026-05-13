package de.joesst.dev.fulfillmenttools.routingplans;

import de.joesst.dev.fulfillmenttools.id.FacilityId;

import java.util.Objects;

/**
 * Request for updating an existing routing plan.
 *
 * <p>The version field is required and used for optimistic locking to prevent concurrent
 * modifications. Use the builder to construct update requests.
 */
public final class UpdateRoutingPlanRequest {

    private final Integer version;
    private final FacilityId facilityRef;
    private final String status;

    /**
     * Creates a new request with the values from the builder.
     *
     * @param builder the builder
     */
    private UpdateRoutingPlanRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.facilityRef = builder.facilityRef;
        this.status = builder.status;
    }

    /**
     * Returns the optimistic-locking version counter.
     *
     * @return the version
     */
    public Integer version() { return version; }

    /**
     * Returns the facility reference to assign.
     *
     * @return the facility reference, or null if not set
     */
    public FacilityId facilityRef() { return facilityRef; }

    /**
     * Returns the new status for the routing plan.
     *
     * @return the status, or null if not set
     */
    public String status() { return status; }

    /**
     * Returns a new builder for constructing {@code UpdateRoutingPlanRequest} instances.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@code UpdateRoutingPlanRequest}.
     *
     * <p>Allows fluent construction of update requests. The version field is required;
     * all other fields are optional.
     */
    public static final class Builder {

        private Integer version;
        private FacilityId facilityRef;
        private String status;

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        /**
         * Sets the optimistic-locking version counter.
         *
         * @param version the version (required)
         * @return this builder
         */
        public Builder version(Integer version) { this.version = version; return this; }

        /**
         * Sets the facility reference to assign.
         *
         * @param facilityRef the facility reference
         * @return this builder
         */
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }

        /**
         * Sets the new status for the routing plan.
         *
         * @param status the new status
         * @return this builder
         */
        public Builder status(String status) { this.status = status; return this; }

        /**
         * Builds the {@code UpdateRoutingPlanRequest}.
         *
         * @return the constructed request
         * @throws NullPointerException if version has not been set
         */
        public UpdateRoutingPlanRequest build() { return new UpdateRoutingPlanRequest(this); }
    }
}
