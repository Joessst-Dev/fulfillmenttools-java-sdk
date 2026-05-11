package de.joesst.dev.fulfillmenttools.reservations;

public final class ReservationListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String facilityRef;

    private ReservationListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.facilityRef = builder.facilityRef;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public String facilityRef() { return facilityRef; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.facilityRef = this.facilityRef;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private String facilityRef;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }

        public ReservationListRequest build() { return new ReservationListRequest(this); }
    }
}
