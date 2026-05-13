package de.joesst.dev.fulfillmenttools.tags;

/**
 * Request parameters for searching tags via {@link TagsClient#search(TagSearchRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * TagSearchRequest request = TagSearchRequest.builder()
 *     .query(TagSearchQuery.builder().idLike("tag*").build())
 *     .size(50)
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class TagSearchRequest {

    private final TagSearchQuery query;
    private final Integer size;
    private final String after;
    private final String before;
    private final Integer last;

    private TagSearchRequest(Builder builder) {
        this.query = builder.query;
        this.size = builder.size;
        this.after = builder.after;
        this.before = builder.before;
        this.last = builder.last;
    }

    /**
     * Returns the search query defining which tags to match.
     *
     * @return the search query, or {@code null} if not set
     */
    public TagSearchQuery query() { return query; }

    /**
     * Returns the maximum number of tags to return per page.
     *
     * @return the page size, or {@code null} if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the cursor for pagination; tags are returned after this cursor.
     *
     * @return the after cursor, or {@code null} if not set
     */
    public String after() { return after; }

    /**
     * Returns the cursor for pagination; tags are returned before this cursor.
     *
     * @return the before cursor, or {@code null} if not set
     */
    public String before() { return before; }

    /**
     * Returns the maximum number of recent tags to include in the result.
     *
     * @return the last count, or {@code null} if not set
     */
    public Integer last() { return last; }

    /**
     * Returns a new builder initialized with the current values of this request.
     *
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.query = this.query;
        b.size = this.size;
        b.after = this.after;
        b.before = this.before;
        b.last = this.last;
        return b;
    }

    /**
     * Returns a new {@link Builder} for constructing a {@code TagSearchRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link TagSearchRequest}.
     */
    public static final class Builder {

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        private TagSearchQuery query;
        private Integer size;
        private String after;
        private String before;
        private Integer last;

        /**
         * Sets the search query defining which tags to match.
         *
         * @param query the search query
         * @return this builder
         */
        public Builder query(TagSearchQuery query) { this.query = query; return this; }

        /**
         * Sets the maximum number of tags to return per page.
         *
         * @param size the page size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the cursor for pagination; tags are returned after this cursor.
         *
         * @param after the after cursor
         * @return this builder
         */
        public Builder after(String after) { this.after = after; return this; }

        /**
         * Sets the cursor for pagination; tags are returned before this cursor.
         *
         * @param before the before cursor
         * @return this builder
         */
        public Builder before(String before) { this.before = before; return this; }

        /**
         * Sets the maximum number of recent tags to include in the result.
         *
         * @param last the last count
         * @return this builder
         */
        public Builder last(Integer last) { this.last = last; return this; }

        /**
         * Builds the {@link TagSearchRequest}.
         *
         * @return a new {@code TagSearchRequest}
         */
        public TagSearchRequest build() { return new TagSearchRequest(this); }
    }
}
