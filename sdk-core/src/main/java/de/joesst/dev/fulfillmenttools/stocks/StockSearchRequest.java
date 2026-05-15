package de.joesst.dev.fulfillmenttools.stocks;

/**
 * Request for {@link StocksClient#search(StockSearchRequest)}.
 *
 * <p>Example:
 * <pre>{@code
 * StockSearchRequest request = StockSearchRequest.builder()
 *     .query(StockSearchQuery.builder()
 *         .tenantArticleIdIn(new TenantArticleId("art-1"), new TenantArticleId("art-2"))
 *         .facilityRefIn(new FacilityId("fac-1"))
 *         .build())
 *     .size(50)
 *     .build();
 * }</pre>
 */
public final class StockSearchRequest {

    private final StockSearchQuery query;
    private final Integer size;
    private final String after;

    private StockSearchRequest(Builder builder) {
        this.query = builder.query;
        this.size = builder.size;
        this.after = builder.after;
    }

    /** Returns the search query, or {@code null} if not set. */
    public StockSearchQuery query() { return query; }

    /** Returns the maximum number of results per page, or {@code null} if not set. */
    public Integer size() { return size; }

    /** Returns the cursor for forward pagination, or {@code null} if not set. */
    public String after() { return after; }

    /** Returns a new builder initialized with the current values of this request. */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.query = this.query;
        b.size = this.size;
        b.after = this.after;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private StockSearchQuery query;
        private Integer size;
        private String after;

        private Builder() {}

        /** Sets the search query. */
        public Builder query(StockSearchQuery query) { this.query = query; return this; }

        /** Sets the maximum number of results per page (1–250; server default is 20). */
        public Builder size(Integer size) { this.size = size; return this; }

        /** Sets the cursor for forward pagination (value of {@code pageInfo.endCursor} from the previous page). */
        public Builder after(String after) { this.after = after; return this; }

        public StockSearchRequest build() { return new StockSearchRequest(this); }
    }
}
