package de.joesst.dev.fulfillmenttools.handoverjobs;

public final class HandoverJobListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String facilityRef;
    private final String status;

    private HandoverJobListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.facilityRef = builder.facilityRef;
        this.status = builder.status;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public String facilityRef() { return facilityRef; }
    public String status() { return status; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.facilityRef = this.facilityRef;
        b.status = this.status;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private String facilityRef;
        private String status;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder status(String status) { this.status = status; return this; }

        public HandoverJobListRequest build() { return new HandoverJobListRequest(this); }
    }
}
