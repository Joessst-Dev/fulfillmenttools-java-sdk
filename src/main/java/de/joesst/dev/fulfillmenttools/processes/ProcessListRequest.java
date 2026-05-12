package de.joesst.dev.fulfillmenttools.processes;

import java.util.List;

public final class ProcessListRequest {

    private final Integer size;
    private final String startAfterId;
    private final List<String> facilityRefs;
    private final List<String> status;
    private final List<String> operativeStatus;
    private final String tenantOrderId;
    private final String searchTerm;

    private ProcessListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.facilityRefs = builder.facilityRefs;
        this.status = builder.status;
        this.operativeStatus = builder.operativeStatus;
        this.tenantOrderId = builder.tenantOrderId;
        this.searchTerm = builder.searchTerm;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public List<String> facilityRefs() { return facilityRefs; }
    public List<String> status() { return status; }
    public List<String> operativeStatus() { return operativeStatus; }
    public String tenantOrderId() { return tenantOrderId; }
    public String searchTerm() { return searchTerm; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.facilityRefs = this.facilityRefs;
        b.status = this.status;
        b.operativeStatus = this.operativeStatus;
        b.tenantOrderId = this.tenantOrderId;
        b.searchTerm = this.searchTerm;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private List<String> facilityRefs;
        private List<String> status;
        private List<String> operativeStatus;
        private String tenantOrderId;
        private String searchTerm;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder facilityRefs(List<String> facilityRefs) { this.facilityRefs = facilityRefs; return this; }
        public Builder status(List<String> status) { this.status = status; return this; }
        public Builder operativeStatus(List<String> operativeStatus) { this.operativeStatus = operativeStatus; return this; }
        public Builder tenantOrderId(String tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }
        public Builder searchTerm(String searchTerm) { this.searchTerm = searchTerm; return this; }

        public ProcessListRequest build() { return new ProcessListRequest(this); }
    }
}
