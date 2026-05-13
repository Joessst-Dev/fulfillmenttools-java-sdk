package de.joesst.dev.fulfillmenttools.facilitydiscounts;

import java.util.List;
import java.util.Objects;

/**
 * Request to create a new facility discount.
 */
public final class CreateFacilityDiscountRequest {

    private final String type;
    private final Integer priority;
    private final FacilityDiscountValue discount;
    private final List<FacilityDiscountContext> context;

    private CreateFacilityDiscountRequest(Builder builder) {
        this.type = Objects.requireNonNull(builder.type, "type");
        this.priority = Objects.requireNonNull(builder.priority, "priority");
        this.discount = Objects.requireNonNull(builder.discount, "discount");
        this.context = builder.context;
    }

    /**
     * Returns the type of the discount.
     *
     * @return the discount type
     */
    public String type() { return type; }

    /**
     * Returns the priority of the discount.
     *
     * @return the priority value
     */
    public Integer priority() { return priority; }

    /**
     * Returns the discount value specification.
     *
     * @return the discount value (absolute or relative)
     */
    public FacilityDiscountValue discount() { return discount; }

    /**
     * Returns the context conditions for this discount.
     *
     * @return the list of context conditions, or null if not set
     */
    public List<FacilityDiscountContext> context() { return context; }

    /**
     * Creates a new builder for constructing CreateFacilityDiscountRequest instances.
     *
     * @return a new Builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for constructing CreateFacilityDiscountRequest.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private String type;
        private Integer priority;
        private FacilityDiscountValue discount;
        private List<FacilityDiscountContext> context;

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
         * Builds a new CreateFacilityDiscountRequest with the configured values.
         *
         * @return a new CreateFacilityDiscountRequest instance
         */
        public CreateFacilityDiscountRequest build() { return new CreateFacilityDiscountRequest(this); }
    }
}
