package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import java.util.List;
import java.util.Objects;

/**
 * Request to update an existing facility discount.
 */
public final class UpdateFacilityDiscountRequest {

    private final Integer version;
    private final String type;
    private final Integer priority;
    private final FacilityDiscountValue discount;
    private final List<FacilityDiscountContext> context;

    private UpdateFacilityDiscountRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version");
        this.type = builder.type;
        this.priority = builder.priority;
        this.discount = builder.discount;
        this.context = builder.context;
    }

    /**
     * Returns the version of the discount to update.
     *
     * @return the version
     */
    public Integer version() { return version; }

    /**
     * Returns the type of the discount.
     *
     * @return the discount type, or null if not set
     */
    public String type() { return type; }

    /**
     * Returns the priority of the discount.
     *
     * @return the priority value, or null if not set
     */
    public Integer priority() { return priority; }

    /**
     * Returns the discount value specification.
     *
     * @return the discount value (absolute or relative), or null if not set
     */
    public FacilityDiscountValue discount() { return discount; }

    /**
     * Returns the context conditions for this discount.
     *
     * @return the list of context conditions, or null if not set
     */
    public List<FacilityDiscountContext> context() { return context; }

    /**
     * Creates a new builder for constructing UpdateFacilityDiscountRequest instances.
     *
     * @return a new Builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for constructing UpdateFacilityDiscountRequest.
     */
    public static final class Builder {
        private Integer version;
        private String type;
        private Integer priority;
        private FacilityDiscountValue discount;
        private List<FacilityDiscountContext> context;

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        /**
         * Sets the version of the discount to update.
         *
         * @param version the version
         * @return this builder instance
         */
        public Builder version(Integer version) { this.version = version; return this; }

        /**
         * Sets the type of the discount.
         *
         * @param type the discount type
         * @return this builder instance
         */
        public Builder type(String type) { this.type = type; return this; }

        /**
         * Sets the priority of the discount.
         *
         * @param priority the priority value
         * @return this builder instance
         */
        public Builder priority(Integer priority) { this.priority = priority; return this; }

        /**
         * Sets the discount value specification.
         *
         * @param discount the discount value (absolute or relative)
         * @return this builder instance
         */
        public Builder discount(FacilityDiscountValue discount) { this.discount = discount; return this; }

        /**
         * Sets the context conditions for this discount.
         *
         * @param context the list of context conditions
         * @return this builder instance
         */
        public Builder context(List<FacilityDiscountContext> context) { this.context = context; return this; }

        /**
         * Builds a new UpdateFacilityDiscountRequest with the configured values.
         *
         * @return a new UpdateFacilityDiscountRequest instance
         */
        public UpdateFacilityDiscountRequest build() { return new UpdateFacilityDiscountRequest(this); }
    }
}
