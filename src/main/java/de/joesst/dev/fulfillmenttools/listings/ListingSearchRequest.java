package de.joesst.dev.fulfillmenttools.listings;

/**
 * Request parameters for searching product listings via
 * {@link ListingsClient#search(ListingSearchRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * ListingSearchRequest request = ListingSearchRequest.builder()
 *     .query(ListingSearchQuery.builder()...build())
 *     .size(50)
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class ListingSearchRequest {

    private final ListingSearchQuery query;
    private final Integer size;
    private final Integer last;
    private final String after;
    private final String before;

    private ListingSearchRequest(Builder builder) {
        this.query = builder.query;
        this.size = builder.size;
        this.last = builder.last;
        this.after = builder.after;
        this.before = builder.before;
    }

    /**
     * Returns the search query defining which listings to match.
     *
     * @return the search query, or {@code null} if not set
     */
    public ListingSearchQuery query() { return query; }

    /**
     * Returns the maximum number of listings to return per page.
     *
     * @return the page size, or {@code null} if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the maximum number of recent listings to include in the result.
     *
     * @return the last count, or {@code null} if not set
     */
    public Integer last() { return last; }

    /**
     * Returns the cursor for pagination; listings are returned after this cursor.
     *
     * @return the after cursor, or {@code null} if not set
     */
    public String after() { return after; }

    /**
     * Returns the cursor for pagination; listings are returned before this cursor.
     *
     * @return the before cursor, or {@code null} if not set
     */
    public String before() { return before; }

    /**
     * Returns a new builder initialized with the current values of this request.
     *
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.query = this.query;
        b.size = this.size;
        b.last = this.last;
        b.after = this.after;
        b.before = this.before;
        return b;
    }

    /**
     * Returns a new {@link Builder} for constructing a {@code ListingSearchRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link ListingSearchRequest}.
     */
    public static final class Builder {
        private ListingSearchQuery query;
        private Integer size;
        private Integer last;
        private String after;
        private String before;

        /** Creates a new Builder instance. */
        public Builder() {}

        /**
         * Sets the search query defining which listings to match.
         *
         * @param query the search query
         * @return this builder
         */
        public Builder query(ListingSearchQuery query) { this.query = query; return this; }

        /**
         * Sets the maximum number of listings to return per page.
         *
         * @param size the page size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the maximum number of recent listings to include in the result.
         *
         * @param last the last count
         * @return this builder
         */
        public Builder last(Integer last) { this.last = last; return this; }

        /**
         * Sets the cursor for pagination; listings are returned after this cursor.
         *
         * @param after the after cursor
         * @return this builder
         */
        public Builder after(String after) { this.after = after; return this; }

        /**
         * Sets the cursor for pagination; listings are returned before this cursor.
         *
         * @param before the before cursor
         * @return this builder
         */
        public Builder before(String before) { this.before = before; return this; }

        /**
         * Builds the {@link ListingSearchRequest}.
         *
         * @return a new {@code ListingSearchRequest}
         */
        public ListingSearchRequest build() { return new ListingSearchRequest(this); }
    }
}
