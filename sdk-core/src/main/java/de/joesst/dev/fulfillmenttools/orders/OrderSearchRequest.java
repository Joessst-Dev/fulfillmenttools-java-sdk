package de.joesst.dev.fulfillmenttools.orders;

import java.util.Objects;

/**
 * Request to search for orders with a given query and pagination options.
 */
public final class OrderSearchRequest {

    private final OrderSearchQuery query;
    private final Integer size;
    private final String after;
    private final String before;
    private final Integer last;

    private OrderSearchRequest(Builder builder) {
        this.query = Objects.requireNonNull(builder.query, "query must not be null");
        this.size = builder.size;
        this.after = builder.after;
        this.before = builder.before;
        this.last = builder.last;
    }

    /**
     * Returns the search query.
     *
     * @return the query (never null)
     */
    public OrderSearchQuery query() { return query; }

    /**
     * Returns the page size.
     *
     * @return the size, or null if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the after cursor for forward pagination.
     *
     * @return the after cursor, or null if not set
     */
    public String after() { return after; }

    /**
     * Returns the before cursor for backward pagination.
     *
     * @return the before cursor, or null if not set
     */
    public String before() { return before; }

    /**
     * Returns the number of last items to retrieve.
     *
     * @return the last count, or null if not set
     */
    public Integer last() { return last; }

    /**
     * Creates a new builder with the same state as this request.
     *
     * @return a new builder
     */
    public Builder toBuilder() {
        return new Builder().query(query).size(size).after(after).before(before).last(last);
    }

    /**
     * Creates a new builder for constructing an {@code OrderSearchRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link OrderSearchRequest}.
     */
    public static final class Builder {
        /** Creates a new Builder. */
        public Builder() {}

        private OrderSearchQuery query;
        private Integer size;
        private String after;
        private String before;
        private Integer last;

        /**
         * Sets the search query.
         *
         * @param query the query (required)
         * @return this builder
         */
        public Builder query(OrderSearchQuery query) { this.query = query; return this; }

        /**
         * Sets the page size.
         *
         * @param size the size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the after cursor for forward pagination.
         *
         * @param after the cursor
         * @return this builder
         */
        public Builder after(String after) { this.after = after; return this; }

        /**
         * Sets the before cursor for backward pagination.
         *
         * @param before the cursor
         * @return this builder
         */
        public Builder before(String before) { this.before = before; return this; }

        /**
         * Sets the number of last items to retrieve.
         *
         * @param last the count
         * @return this builder
         */
        public Builder last(Integer last) { this.last = last; return this; }

        /**
         * Builds the {@link OrderSearchRequest}.
         *
         * @return a new request
         */
        public OrderSearchRequest build() { return new OrderSearchRequest(this); }
    }
}
