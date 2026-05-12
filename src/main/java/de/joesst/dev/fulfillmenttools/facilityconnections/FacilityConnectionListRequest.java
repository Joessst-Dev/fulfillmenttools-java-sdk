package de.joesst.dev.fulfillmenttools.facilityconnections;

public final class FacilityConnectionListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String targetFacilityRef;

    private FacilityConnectionListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.targetFacilityRef = builder.targetFacilityRef;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public String targetFacilityRef() { return targetFacilityRef; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.targetFacilityRef = this.targetFacilityRef;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Integer size;
        private String startAfterId;
        private String targetFacilityRef;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder targetFacilityRef(String targetFacilityRef) { this.targetFacilityRef = targetFacilityRef; return this; }

        public FacilityConnectionListRequest build() { return new FacilityConnectionListRequest(this); }
    }
}
