package de.joesst.dev.fulfillmenttools.facilities;

import java.util.Objects;

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

    public FacilitySearchQuery query() { return query; }
    public Integer size() { return size; }
    public String after() { return after; }
    public String before() { return before; }
    public Integer last() { return last; }

    public Builder toBuilder() {
        return new Builder().query(query).size(size).after(after).before(before).last(last);
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private FacilitySearchQuery query;
        private Integer size;
        private String after;
        private String before;
        private Integer last;

        public Builder query(FacilitySearchQuery query) { this.query = query; return this; }
        public Builder size(Integer size) { this.size = size; return this; }
        public Builder after(String after) { this.after = after; return this; }
        public Builder before(String before) { this.before = before; return this; }
        public Builder last(Integer last) { this.last = last; return this; }

        public FacilitySearchRequest build() { return new FacilitySearchRequest(this); }
    }
}
