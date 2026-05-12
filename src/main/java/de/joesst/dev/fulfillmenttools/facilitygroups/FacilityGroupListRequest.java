package de.joesst.dev.fulfillmenttools.facilitygroups;

public final class FacilityGroupListRequest {

    private final Integer size;
    private final String startAfterId;

    private FacilityGroupListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Integer size;
        private String startAfterId;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        public FacilityGroupListRequest build() { return new FacilityGroupListRequest(this); }
    }
}
