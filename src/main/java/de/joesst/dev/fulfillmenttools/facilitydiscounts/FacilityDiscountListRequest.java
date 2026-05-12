package de.joesst.dev.fulfillmenttools.facilitydiscounts;

public final class FacilityDiscountListRequest {

    private final Integer size;
    private final String startAfterId;

    private FacilityDiscountListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }

    public Builder toBuilder() {
        return new Builder().size(size).startAfterId(startAfterId);
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Integer size;
        private String startAfterId;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public FacilityDiscountListRequest build() { return new FacilityDiscountListRequest(this); }
    }
}
