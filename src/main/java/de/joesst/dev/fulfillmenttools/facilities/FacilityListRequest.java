package de.joesst.dev.fulfillmenttools.facilities;

import java.util.List;

public final class FacilityListRequest {

    private final Integer size;
    private final String startAfterId;
    private final List<String> status;
    private final String tenantFacilityId;
    private final List<String> type;
    private final String orderBy;

    private FacilityListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.status = builder.status;
        this.tenantFacilityId = builder.tenantFacilityId;
        this.type = builder.type;
        this.orderBy = builder.orderBy;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public List<String> status() { return status; }
    public String tenantFacilityId() { return tenantFacilityId; }
    public List<String> type() { return type; }
    public String orderBy() { return orderBy; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.status = this.status;
        b.tenantFacilityId = this.tenantFacilityId;
        b.type = this.type;
        b.orderBy = this.orderBy;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private List<String> status;
        private String tenantFacilityId;
        private List<String> type;
        private String orderBy;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder status(List<String> status) { this.status = status; return this; }
        public Builder tenantFacilityId(String tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }
        public Builder type(List<String> type) { this.type = type; return this; }
        public Builder orderBy(String orderBy) { this.orderBy = orderBy; return this; }

        public FacilityListRequest build() { return new FacilityListRequest(this); }
    }
}
