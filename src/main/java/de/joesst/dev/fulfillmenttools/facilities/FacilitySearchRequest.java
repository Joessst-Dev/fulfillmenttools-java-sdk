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

    /** Returns the search query. @return the query */
    public FacilitySearchQuery query() { return query; }

    /** Returns the page size. @return the size */
    public Integer size() { return size; }

    /** Returns the cursor for pagination forward. @return the after cursor */
    public String after() { return after; }

    /** Returns the cursor for pagination backward. @return the before cursor */
    public String before() { return before; }

    /** Returns the count of items to return from the end. @return the last count */
    public Integer last() { return last; }

    public Builder toBuilder() {
        return new Builder().query(query).size(size).after(after).before(before).last(last);
    }

    public static Builder builder() { return new Builder(); }

    /** Builder for FacilitySearchRequest. */
    public static final class Builder {

        /** The search query. */
        private FacilitySearchQuery query;

        /** The page size. */
        private Integer size;

        /** The cursor for pagination forward. */
        private String after;

        /** The cursor for pagination backward. */
        private String before;

        /** The count of items to return from the end. */
        private Integer last;

        /** Creates a new Builder. */
        public Builder() {}

        /** Sets the search query. @param query the query. @return this builder */
        public Builder query(FacilitySearchQuery query) { this.query = query; return this; }

        /** Sets the page size. @param size the size. @return this builder */
        public Builder size(Integer size) { this.size = size; return this; }

        /** Sets the cursor for pagination forward. @param after the after cursor. @return this builder */
        public Builder after(String after) { this.after = after; return this; }

        /** Sets the cursor for pagination backward. @param before the before cursor. @return this builder */
        public Builder before(String before) { this.before = before; return this; }

        /** Sets the count of items to return from the end. @param last the last count. @return this builder */
        public Builder last(Integer last) { this.last = last; return this; }

        /** Builds and returns a new FacilitySearchRequest. @return the built request */
        public FacilitySearchRequest build() { return new FacilitySearchRequest(this); }
    }
}
