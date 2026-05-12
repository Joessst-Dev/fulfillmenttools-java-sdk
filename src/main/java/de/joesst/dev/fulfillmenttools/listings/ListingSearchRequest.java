package de.joesst.dev.fulfillmenttools.listings;

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

    public ListingSearchQuery query() { return query; }
    public Integer size() { return size; }
    public Integer last() { return last; }
    public String after() { return after; }
    public String before() { return before; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.query = this.query;
        b.size = this.size;
        b.last = this.last;
        b.after = this.after;
        b.before = this.before;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private ListingSearchQuery query;
        private Integer size;
        private Integer last;
        private String after;
        private String before;

        public Builder query(ListingSearchQuery query) { this.query = query; return this; }
        public Builder size(Integer size) { this.size = size; return this; }
        public Builder last(Integer last) { this.last = last; return this; }
        public Builder after(String after) { this.after = after; return this; }
        public Builder before(String before) { this.before = before; return this; }

        public ListingSearchRequest build() { return new ListingSearchRequest(this); }
    }
}
