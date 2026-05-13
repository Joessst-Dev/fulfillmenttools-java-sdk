package de.joesst.dev.fulfillmenttools.facilities;

import java.util.Objects;

/**
 * Request payload for searching facilities with cursor-based pagination.
 *
 * <p>Use {@link #builder()} to construct instances fluently.
 * The {@code query} field is required; pagination fields are optional.
 *
 * <p>Example:
 * <pre>{@code
 * var query = FacilitySearchQuery.builder()
 *     .nameEq("Main Warehouse")
 *     .build();
 * var request = FacilitySearchRequest.builder()
 *     .query(query)
 *     .size(25)
 *     .build();
 * }</pre>
 */
public final class FacilitySearchRequest {

    private final FacilitySearchQuery query;
    private final Integer size;
    private final String after;
    private final String before;
    private final Integer last;

    private FacilitySearchRequest(Builder builder) {
        this.query = Objects.requireNonNull(builder.query, "query must not be null");
        this.size = builder.size;
        this.after = builder.after;
        this.before = builder.before;
        this.last = builder.last;
    }

    /**
     * Returns the search query.
     * @return the query; never {@code null}
     */
    public FacilitySearchQuery query() { return query; }

    /**
     * Returns the page size.
     * @return the size, or {@code null} if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the cursor for forward pagination.
     * @return the after cursor, or {@code null} if not set
     */
    public String after() { return after; }

    /**
     * Returns the cursor for backward pagination.
     * @return the before cursor, or {@code null} if not set
     */
    public String before() { return before; }

    /**
     * Returns the count of items to return from the end.
     * @return the last count, or {@code null} if not set
     */
    public Integer last() { return last; }

    /**
     * Returns a new builder initialized with this request's current values.
     * @return a new builder
     */
    public Builder toBuilder() {
        return new Builder().query(query).size(size).after(after).before(before).last(last);
    }

    /**
     * Returns a new builder for constructing a {@code FacilitySearchRequest}.
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /** Builder for {@code FacilitySearchRequest}. */
    public static final class Builder {

        private FacilitySearchQuery query;
        private Integer size;
        private String after;
        private String before;
        private Integer last;

        /** Creates a new Builder instance. */
        public Builder() {}

        /**
         * Sets the search query (required).
         * @param query the search query
         * @return this builder
         */
        public Builder query(FacilitySearchQuery query) { this.query = query; return this; }

        /**
         * Sets the page size.
         * @param size the page size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the cursor for forward pagination.
         * @param after the after cursor
         * @return this builder
         */
        public Builder after(String after) { this.after = after; return this; }

        /**
         * Sets the cursor for backward pagination.
         * @param before the before cursor
         * @return this builder
         */
        public Builder before(String before) { this.before = before; return this; }

        /**
         * Sets the count of items to return from the end.
         * @param last the last count
         * @return this builder
         */
        public Builder last(Integer last) { this.last = last; return this; }

        /**
         * Builds and returns a new {@code FacilitySearchRequest}.
         * @return the built request
         * @throws NullPointerException if query has not been set
         */
        public FacilitySearchRequest build() { return new FacilitySearchRequest(this); }
    }
}
