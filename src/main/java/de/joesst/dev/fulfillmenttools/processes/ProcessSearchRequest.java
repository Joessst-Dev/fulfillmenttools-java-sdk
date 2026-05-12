package de.joesst.dev.fulfillmenttools.processes;

import java.util.List;

public final class ProcessSearchRequest {

    private final List<String> facilityRefs;
    private final List<String> status;
    private final Integer size;
    private final String startAfterId;

    private ProcessSearchRequest(Builder builder) {
        this.facilityRefs = builder.facilityRefs;
        this.status = builder.status;
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
    }

    public List<String> facilityRefs() { return facilityRefs; }
    public List<String> status() { return status; }
    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private List<String> facilityRefs;
        private List<String> status;
        private Integer size;
        private String startAfterId;

        public Builder facilityRefs(List<String> facilityRefs) { this.facilityRefs = facilityRefs; return this; }
        public Builder status(List<String> status) { this.status = status; return this; }
        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        public ProcessSearchRequest build() { return new ProcessSearchRequest(this); }
    }
}
