package de.joesst.dev.fulfillmenttools.handoverjobs;

import java.util.List;

public final class HandoverJobListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String facilityRef;
    private final List<String> status;
    private final String pickJobRef;
    private final String shipmentRef;
    private final String assignedUser;
    private final List<String> carrierRefs;
    private final String channel;
    private final Boolean anonymized;
    private final String tenantOrderId;
    private final String searchTerm;
    private final String startTargetTime;
    private final String endTargetTime;

    private HandoverJobListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.facilityRef = builder.facilityRef;
        this.status = builder.status;
        this.pickJobRef = builder.pickJobRef;
        this.shipmentRef = builder.shipmentRef;
        this.assignedUser = builder.assignedUser;
        this.carrierRefs = builder.carrierRefs;
        this.channel = builder.channel;
        this.anonymized = builder.anonymized;
        this.tenantOrderId = builder.tenantOrderId;
        this.searchTerm = builder.searchTerm;
        this.startTargetTime = builder.startTargetTime;
        this.endTargetTime = builder.endTargetTime;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public String facilityRef() { return facilityRef; }
    public List<String> status() { return status; }
    public String pickJobRef() { return pickJobRef; }
    public String shipmentRef() { return shipmentRef; }
    public String assignedUser() { return assignedUser; }
    public List<String> carrierRefs() { return carrierRefs; }
    public String channel() { return channel; }
    public Boolean anonymized() { return anonymized; }
    public String tenantOrderId() { return tenantOrderId; }
    public String searchTerm() { return searchTerm; }
    public String startTargetTime() { return startTargetTime; }
    public String endTargetTime() { return endTargetTime; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.facilityRef = this.facilityRef;
        b.status = this.status;
        b.pickJobRef = this.pickJobRef;
        b.shipmentRef = this.shipmentRef;
        b.assignedUser = this.assignedUser;
        b.carrierRefs = this.carrierRefs;
        b.channel = this.channel;
        b.anonymized = this.anonymized;
        b.tenantOrderId = this.tenantOrderId;
        b.searchTerm = this.searchTerm;
        b.startTargetTime = this.startTargetTime;
        b.endTargetTime = this.endTargetTime;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private String facilityRef;
        private List<String> status;
        private String pickJobRef;
        private String shipmentRef;
        private String assignedUser;
        private List<String> carrierRefs;
        private String channel;
        private Boolean anonymized;
        private String tenantOrderId;
        private String searchTerm;
        private String startTargetTime;
        private String endTargetTime;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder status(List<String> status) { this.status = status; return this; }
        public Builder pickJobRef(String pickJobRef) { this.pickJobRef = pickJobRef; return this; }
        public Builder shipmentRef(String shipmentRef) { this.shipmentRef = shipmentRef; return this; }
        public Builder assignedUser(String assignedUser) { this.assignedUser = assignedUser; return this; }
        public Builder carrierRefs(List<String> carrierRefs) { this.carrierRefs = carrierRefs; return this; }
        public Builder channel(String channel) { this.channel = channel; return this; }
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }
        public Builder tenantOrderId(String tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }
        public Builder searchTerm(String searchTerm) { this.searchTerm = searchTerm; return this; }
        public Builder startTargetTime(String startTargetTime) { this.startTargetTime = startTargetTime; return this; }
        public Builder endTargetTime(String endTargetTime) { this.endTargetTime = endTargetTime; return this; }

        public HandoverJobListRequest build() { return new HandoverJobListRequest(this); }
    }
}
