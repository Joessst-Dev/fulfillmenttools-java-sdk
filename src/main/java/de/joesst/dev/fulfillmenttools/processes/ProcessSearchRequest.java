package de.joesst.dev.fulfillmenttools.processes;

public final class ProcessSearchRequest {

    private final String facilityRef;
    private final String status;
    private final Integer size;
    private final String startAfterId;

    private ProcessSearchRequest(Builder builder) {
        this.facilityRef = builder.facilityRef;
        this.status = builder.status;
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
    }

    public String facilityRef() { return facilityRef; }
    public String status() { return status; }
    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String facilityRef;
        private String status;
        private Integer size;
        private String startAfterId;

        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        public ProcessSearchRequest build() { return new ProcessSearchRequest(this); }
    }
}
