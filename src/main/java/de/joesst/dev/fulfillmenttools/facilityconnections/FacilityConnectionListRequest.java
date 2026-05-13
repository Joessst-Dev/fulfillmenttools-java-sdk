package de.joesst.dev.fulfillmenttools.facilityconnections;

/**
 * Request payload for listing facility connections with optional filtering and pagination.
 *
 * <p>Use {@link #builder()} to construct instances fluently.
 * All fields are optional.
 *
 * <p>Example:
 * <pre>{@code
 * var request = FacilityConnectionListRequest.builder()
 *     .targetFacilityRef("fac-456")
 *     .size(50)
 *     .startAfterId("last-connection-id")
 *     .build();
 * }</pre>
 */
public final class FacilityConnectionListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String targetFacilityRef;

    private FacilityConnectionListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.targetFacilityRef = builder.targetFacilityRef;
    }

    /**
     * Returns the page size limit.
     * @return the size limit, or {@code null} if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the cursor for pagination.
     * @return the start-after ID, or {@code null} if not set
     */
    public String startAfterId() { return startAfterId; }

    /**
     * Returns the target facility reference filter.
     * @return the target facility reference, or {@code null} if not set
     */
    public String targetFacilityRef() { return targetFacilityRef; }

    /**
     * Returns a new builder initialized with this request's current values.
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.targetFacilityRef = this.targetFacilityRef;
        return b;
    }

    /**
     * Returns a new builder for constructing a {@code FacilityConnectionListRequest}.
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /** Builder for {@code FacilityConnectionListRequest}. */
    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private String targetFacilityRef;

        /** Creates a new Builder instance. */
        public Builder() {}

        /**
         * Sets the page size limit.
         * @param size the page size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the cursor for pagination.
         * @param startAfterId the start-after ID
         * @return this builder
         */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /**
         * Sets the target facility reference filter.
         * @param targetFacilityRef the target facility reference
         * @return this builder
         */
        public Builder targetFacilityRef(String targetFacilityRef) { this.targetFacilityRef = targetFacilityRef; return this; }

        /**
         * Builds and returns a new {@code FacilityConnectionListRequest}.
         * @return the built request
         */
        public FacilityConnectionListRequest build() { return new FacilityConnectionListRequest(this); }
    }
}
