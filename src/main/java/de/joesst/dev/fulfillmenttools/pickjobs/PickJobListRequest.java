package de.joesst.dev.fulfillmenttools.pickjobs;

import java.util.List;

public final class PickJobListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String facilityRef;
    private final List<String> status;
    private final String orderRef;
    private final String tenantOrderId;
    private final String assignedUser;
    private final String searchTerm;
    private final String channel;
    private final String consumerName;
    private final String shortId;
    private final String articleTitle;
    private final Boolean anonymized;
    private final String orderBy;
    private final String modifiedByUsername;
    private final String startOrderDate;
    private final String endOrderDate;
    private final String startTargetTime;
    private final String endTargetTime;
    private final List<String> carrierKeys;
    private final List<String> zoneRefs;
    private final List<String> pickJobRefs;

    private PickJobListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.facilityRef = builder.facilityRef;
        this.status = builder.status;
        this.orderRef = builder.orderRef;
        this.tenantOrderId = builder.tenantOrderId;
        this.assignedUser = builder.assignedUser;
        this.searchTerm = builder.searchTerm;
        this.channel = builder.channel;
        this.consumerName = builder.consumerName;
        this.shortId = builder.shortId;
        this.articleTitle = builder.articleTitle;
        this.anonymized = builder.anonymized;
        this.orderBy = builder.orderBy;
        this.modifiedByUsername = builder.modifiedByUsername;
        this.startOrderDate = builder.startOrderDate;
        this.endOrderDate = builder.endOrderDate;
        this.startTargetTime = builder.startTargetTime;
        this.endTargetTime = builder.endTargetTime;
        this.carrierKeys = builder.carrierKeys;
        this.zoneRefs = builder.zoneRefs;
        this.pickJobRefs = builder.pickJobRefs;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public String facilityRef() { return facilityRef; }
    public List<String> status() { return status; }
    public String orderRef() { return orderRef; }
    public String tenantOrderId() { return tenantOrderId; }
    public String assignedUser() { return assignedUser; }
    public String searchTerm() { return searchTerm; }
    public String channel() { return channel; }
    public String consumerName() { return consumerName; }
    public String shortId() { return shortId; }
    public String articleTitle() { return articleTitle; }
    public Boolean anonymized() { return anonymized; }
    public String orderBy() { return orderBy; }
    public String modifiedByUsername() { return modifiedByUsername; }
    public String startOrderDate() { return startOrderDate; }
    public String endOrderDate() { return endOrderDate; }
    public String startTargetTime() { return startTargetTime; }
    public String endTargetTime() { return endTargetTime; }
    public List<String> carrierKeys() { return carrierKeys; }
    public List<String> zoneRefs() { return zoneRefs; }
    public List<String> pickJobRefs() { return pickJobRefs; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.facilityRef = this.facilityRef;
        b.status = this.status;
        b.orderRef = this.orderRef;
        b.tenantOrderId = this.tenantOrderId;
        b.assignedUser = this.assignedUser;
        b.searchTerm = this.searchTerm;
        b.channel = this.channel;
        b.consumerName = this.consumerName;
        b.shortId = this.shortId;
        b.articleTitle = this.articleTitle;
        b.anonymized = this.anonymized;
        b.orderBy = this.orderBy;
        b.modifiedByUsername = this.modifiedByUsername;
        b.startOrderDate = this.startOrderDate;
        b.endOrderDate = this.endOrderDate;
        b.startTargetTime = this.startTargetTime;
        b.endTargetTime = this.endTargetTime;
        b.carrierKeys = this.carrierKeys;
        b.zoneRefs = this.zoneRefs;
        b.pickJobRefs = this.pickJobRefs;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private String facilityRef;
        private List<String> status;
        private String orderRef;
        private String tenantOrderId;
        private String assignedUser;
        private String searchTerm;
        private String channel;
        private String consumerName;
        private String shortId;
        private String articleTitle;
        private Boolean anonymized;
        private String orderBy;
        private String modifiedByUsername;
        private String startOrderDate;
        private String endOrderDate;
        private String startTargetTime;
        private String endTargetTime;
        private List<String> carrierKeys;
        private List<String> zoneRefs;
        private List<String> pickJobRefs;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder status(List<String> status) { this.status = status; return this; }
        public Builder orderRef(String orderRef) { this.orderRef = orderRef; return this; }
        public Builder tenantOrderId(String tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }
        public Builder assignedUser(String assignedUser) { this.assignedUser = assignedUser; return this; }
        public Builder searchTerm(String searchTerm) { this.searchTerm = searchTerm; return this; }
        public Builder channel(String channel) { this.channel = channel; return this; }
        public Builder consumerName(String consumerName) { this.consumerName = consumerName; return this; }
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }
        public Builder articleTitle(String articleTitle) { this.articleTitle = articleTitle; return this; }
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }
        public Builder orderBy(String orderBy) { this.orderBy = orderBy; return this; }
        public Builder modifiedByUsername(String modifiedByUsername) { this.modifiedByUsername = modifiedByUsername; return this; }
        public Builder startOrderDate(String startOrderDate) { this.startOrderDate = startOrderDate; return this; }
        public Builder endOrderDate(String endOrderDate) { this.endOrderDate = endOrderDate; return this; }
        public Builder startTargetTime(String startTargetTime) { this.startTargetTime = startTargetTime; return this; }
        public Builder endTargetTime(String endTargetTime) { this.endTargetTime = endTargetTime; return this; }
        public Builder carrierKeys(List<String> carrierKeys) { this.carrierKeys = carrierKeys; return this; }
        public Builder zoneRefs(List<String> zoneRefs) { this.zoneRefs = zoneRefs; return this; }
        public Builder pickJobRefs(List<String> pickJobRefs) { this.pickJobRefs = pickJobRefs; return this; }

        public PickJobListRequest build() { return new PickJobListRequest(this); }
    }
}
