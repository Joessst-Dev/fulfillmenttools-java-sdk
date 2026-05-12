package de.joesst.dev.fulfillmenttools.facilitygroups;

public final class FacilityGroupSearchRequest {

    private final FacilityGroupSearchQuery query;
    private final Integer size;
    private final String after;
    private final String before;

    private FacilityGroupSearchRequest(Builder builder) {
        this.query = builder.query;
        this.size = builder.size;
        this.after = builder.after;
        this.before = builder.before;
    }

    public FacilityGroupSearchQuery query() { return query; }
    public Integer size() { return size; }
    public String after() { return after; }
    public String before() { return before; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.query = this.query;
        b.size = this.size;
        b.after = this.after;
        b.before = this.before;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private FacilityGroupSearchQuery query;
        private Integer size;
        private String after;
        private String before;

        public Builder query(FacilityGroupSearchQuery query) { this.query = query; return this; }
        public Builder size(Integer size) { this.size = size; return this; }
        public Builder after(String after) { this.after = after; return this; }
        public Builder before(String before) { this.before = before; return this; }

        public FacilityGroupSearchRequest build() { return new FacilityGroupSearchRequest(this); }
    }
}
