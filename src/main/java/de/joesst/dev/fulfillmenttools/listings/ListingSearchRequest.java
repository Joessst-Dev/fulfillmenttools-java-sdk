package de.joesst.dev.fulfillmenttools.listings;

public final class ListingSearchRequest {

    private final String facilityRef;
    private final String tenantArticleId;
    private final Integer size;
    private final String startAfterId;

    private ListingSearchRequest(Builder builder) {
        this.facilityRef = builder.facilityRef;
        this.tenantArticleId = builder.tenantArticleId;
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
    }

    public String facilityRef() { return facilityRef; }
    public String tenantArticleId() { return tenantArticleId; }
    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.facilityRef = this.facilityRef;
        b.tenantArticleId = this.tenantArticleId;
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String facilityRef;
        private String tenantArticleId;
        private Integer size;
        private String startAfterId;

        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder tenantArticleId(String tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        public ListingSearchRequest build() { return new ListingSearchRequest(this); }
    }
}
