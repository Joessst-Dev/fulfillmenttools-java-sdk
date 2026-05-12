package de.joesst.dev.fulfillmenttools.tags;

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

    public TagSearchQuery query() { return query; }
    public Integer size() { return size; }
    public String after() { return after; }
    public String before() { return before; }
    public Integer last() { return last; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.query = this.query;
        b.size = this.size;
        b.after = this.after;
        b.before = this.before;
        b.last = this.last;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private TagSearchQuery query;
        private Integer size;
        private String after;
        private String before;
        private Integer last;

        public Builder query(TagSearchQuery query) { this.query = query; return this; }
        public Builder size(Integer size) { this.size = size; return this; }
        public Builder after(String after) { this.after = after; return this; }
        public Builder before(String before) { this.before = before; return this; }
        public Builder last(Integer last) { this.last = last; return this; }

        public TagSearchRequest build() { return new TagSearchRequest(this); }
    }
}
