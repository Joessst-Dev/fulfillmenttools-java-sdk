package de.joesst.dev.fulfillmenttools.facilitydiscounts;

/**
 * Request to list facility discounts with pagination support.
 */
public final class FacilityDiscountListRequest {

    private final Integer size;
    private final String startAfterId;

    private FacilityDiscountListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
    }

    /**
     * Returns the maximum number of items to return per page.
     *
     * @return the page size, or null if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the discount ID to start listing after.
     *
     * @return the start after ID, or null if not set
     */
    public String startAfterId() { return startAfterId; }

    /**
     * Returns a builder initialized with the current request values.
     *
     * @return a new Builder with the current values
     */
    public Builder toBuilder() {
        return new Builder().size(size).startAfterId(startAfterId);
    }

    /**
     * Creates a new builder for constructing FacilityDiscountListRequest instances.
     *
     * @return a new Builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for constructing FacilityDiscountListRequest.
     */
    public static final class Builder {
        private Integer size;
        private String startAfterId;

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        /**
         * Sets the maximum number of items to return per page.
         *
         * @param size the page size
         * @return this builder instance
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the discount ID to start listing after.
         *
         * @param startAfterId the start after ID for pagination
         * @return this builder instance
         */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /**
         * Builds a new FacilityDiscountListRequest with the configured values.
         *
         * @return a new FacilityDiscountListRequest instance
         */
        public FacilityDiscountListRequest build() { return new FacilityDiscountListRequest(this); }
    }
}
