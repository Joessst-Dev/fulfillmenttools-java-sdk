package de.joesst.dev.fulfillmenttools.facilities;

import java.util.Objects;

public final class FacilitySearchRequest {

    private final FacilitySearchQuery query;
    private final Integer size;
    private final String after;
    private final String before;

    private FacilitySearchRequest(Builder builder) {
        this.query = Objects.requireNonNull(builder.query, "query must not be null");
        this.size = builder.size;
        this.after = builder.after;
        this.before = builder.before;
    }

    public FacilitySearchQuery query() { return query; }
    public Integer size() { return size; }
    public String after() { return after; }
    public String before() { return before; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private FacilitySearchQuery query;
        private Integer size;
        private String after;
        private String before;

        public Builder query(FacilitySearchQuery query) { this.query = query; return this; }
        public Builder size(Integer size) { this.size = size; return this; }
        public Builder after(String after) { this.after = after; return this; }
        public Builder before(String before) { this.before = before; return this; }

        public FacilitySearchRequest build() { return new FacilitySearchRequest(this); }
    }
}
