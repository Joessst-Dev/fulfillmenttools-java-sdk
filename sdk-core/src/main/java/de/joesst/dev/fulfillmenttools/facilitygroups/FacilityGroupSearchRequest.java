package de.joesst.dev.fulfillmenttools.facilitygroups;

/**
 * Request to search facility groups with a query and pagination support.
 */
public final class FacilityGroupSearchRequest {

    private final FacilityGroupSearchQuery query;
    private final Integer size;
    private final String after;
    private final String before;

    private FacilityGroupSearchRequest(Builder builder) {
        this.query = builder.query;
        this.size = builder.size;
        this.after = builder.after;
        this.before = builder.before;
    }

    /**
     * Returns the search query for facility groups.
     *
     * @return the search query, or null if not set
     */
    public FacilityGroupSearchQuery query() { return query; }

    /**
     * Returns the maximum number of items to return per page.
     *
     * @return the page size, or null if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the cursor for pagination to retrieve items after this cursor.
     *
     * @return the after cursor, or null if not set
     */
    public String after() { return after; }

    /**
     * Returns the cursor for pagination to retrieve items before this cursor.
     *
     * @return the before cursor, or null if not set
     */
    public String before() { return before; }

    /**
     * Returns a builder initialized with the current request values.
     *
     * @return a new Builder with the current values
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.query = this.query;
        b.size = this.size;
        b.after = this.after;
        b.before = this.before;
        return b;
    }

    /**
     * Creates a new builder for constructing FacilityGroupSearchRequest instances.
     *
     * @return a new Builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for constructing FacilityGroupSearchRequest.
     */
    public static final class Builder {
        /**
         * Creates a new Builder.
         */
        public Builder() {}

        private FacilityGroupSearchQuery query;
        private Integer size;
        private String after;
        private String before;

        /**
         * Sets the search query for facility groups.
         *
         * @param query the search query
         * @return this builder instance
         */
        public Builder query(FacilityGroupSearchQuery query) { this.query = query; return this; }

        /**
         * Sets the maximum number of items to return per page.
         *
         * @param size the page size
         * @return this builder instance
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the cursor for pagination to retrieve items after this cursor.
         *
         * @param after the after cursor
         * @return this builder instance
         */
        public Builder after(String after) { this.after = after; return this; }

        /**
         * Sets the cursor for pagination to retrieve items before this cursor.
         *
         * @param before the before cursor
         * @return this builder instance
         */
        public Builder before(String before) { this.before = before; return this; }

        /**
         * Builds a new FacilityGroupSearchRequest with the configured values.
         *
         * @return a new FacilityGroupSearchRequest instance
         */
        public FacilityGroupSearchRequest build() { return new FacilityGroupSearchRequest(this); }
    }
}
