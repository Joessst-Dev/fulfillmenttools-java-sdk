package de.joesst.dev.fulfillmenttools.stocks;

import java.util.List;

public final class StockListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String facilityRef;
    private final String tenantFacilityId;
    private final List<String> tenantArticleId;
    private final List<String> locationRef;

    private StockListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.facilityRef = builder.facilityRef;
        this.tenantFacilityId = builder.tenantFacilityId;
        this.tenantArticleId = builder.tenantArticleId;
        this.locationRef = builder.locationRef;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public String facilityRef() { return facilityRef; }
    public String tenantFacilityId() { return tenantFacilityId; }
    public List<String> tenantArticleId() { return tenantArticleId; }
    public List<String> locationRef() { return locationRef; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.facilityRef = this.facilityRef;
        b.tenantFacilityId = this.tenantFacilityId;
        b.tenantArticleId = this.tenantArticleId;
        b.locationRef = this.locationRef;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private String facilityRef;
        private String tenantFacilityId;
        private List<String> tenantArticleId;
        private List<String> locationRef;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder tenantFacilityId(String tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }
        public Builder tenantArticleId(List<String> tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder locationRef(List<String> locationRef) { this.locationRef = locationRef; return this; }

        public StockListRequest build() { return new StockListRequest(this); }
    }
}
