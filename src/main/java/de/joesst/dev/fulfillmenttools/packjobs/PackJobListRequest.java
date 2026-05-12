package de.joesst.dev.fulfillmenttools.packjobs;

import java.util.List;

public final class PackJobListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String facilityRef;
    private final List<String> status;
    private final Boolean anonymized;
    private final String assignedUser;
    private final String searchTerm;
    private final String channel;
    private final List<String> sourceContainerCodes;
    private final String orderBy;
    private final String startTargetTime;
    private final String endTargetTime;
    private final String orderRef;
    private final List<String> packJobIds;
    private final String processId;
    private final String pickJobRef;
    private final String shortId;
    private final String articleTitle;
    private final String startOrderDate;
    private final String endOrderDate;
    private final String modifiedByUsername;
    private final String tenantOrderId;

    private PackJobListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.facilityRef = builder.facilityRef;
        this.status = builder.status;
        this.anonymized = builder.anonymized;
        this.assignedUser = builder.assignedUser;
        this.searchTerm = builder.searchTerm;
        this.channel = builder.channel;
        this.sourceContainerCodes = builder.sourceContainerCodes;
        this.orderBy = builder.orderBy;
        this.startTargetTime = builder.startTargetTime;
        this.endTargetTime = builder.endTargetTime;
        this.orderRef = builder.orderRef;
        this.packJobIds = builder.packJobIds;
        this.processId = builder.processId;
        this.pickJobRef = builder.pickJobRef;
        this.shortId = builder.shortId;
        this.articleTitle = builder.articleTitle;
        this.startOrderDate = builder.startOrderDate;
        this.endOrderDate = builder.endOrderDate;
        this.modifiedByUsername = builder.modifiedByUsername;
        this.tenantOrderId = builder.tenantOrderId;
    }

    public Integer size() { return size; }
    public String startAfterId() { return startAfterId; }
    public String facilityRef() { return facilityRef; }
    public List<String> status() { return status; }
    public Boolean anonymized() { return anonymized; }
    public String assignedUser() { return assignedUser; }
    public String searchTerm() { return searchTerm; }
    public String channel() { return channel; }
    public List<String> sourceContainerCodes() { return sourceContainerCodes; }
    public String orderBy() { return orderBy; }
    public String startTargetTime() { return startTargetTime; }
    public String endTargetTime() { return endTargetTime; }
    public String orderRef() { return orderRef; }
    public List<String> packJobIds() { return packJobIds; }
    public String processId() { return processId; }
    public String pickJobRef() { return pickJobRef; }
    public String shortId() { return shortId; }
    public String articleTitle() { return articleTitle; }
    public String startOrderDate() { return startOrderDate; }
    public String endOrderDate() { return endOrderDate; }
    public String modifiedByUsername() { return modifiedByUsername; }
    public String tenantOrderId() { return tenantOrderId; }

    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.facilityRef = this.facilityRef;
        b.status = this.status;
        b.anonymized = this.anonymized;
        b.assignedUser = this.assignedUser;
        b.searchTerm = this.searchTerm;
        b.channel = this.channel;
        b.sourceContainerCodes = this.sourceContainerCodes;
        b.orderBy = this.orderBy;
        b.startTargetTime = this.startTargetTime;
        b.endTargetTime = this.endTargetTime;
        b.orderRef = this.orderRef;
        b.packJobIds = this.packJobIds;
        b.processId = this.processId;
        b.pickJobRef = this.pickJobRef;
        b.shortId = this.shortId;
        b.articleTitle = this.articleTitle;
        b.startOrderDate = this.startOrderDate;
        b.endOrderDate = this.endOrderDate;
        b.modifiedByUsername = this.modifiedByUsername;
        b.tenantOrderId = this.tenantOrderId;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private String facilityRef;
        private List<String> status;
        private Boolean anonymized;
        private String assignedUser;
        private String searchTerm;
        private String channel;
        private List<String> sourceContainerCodes;
        private String orderBy;
        private String startTargetTime;
        private String endTargetTime;
        private String orderRef;
        private List<String> packJobIds;
        private String processId;
        private String pickJobRef;
        private String shortId;
        private String articleTitle;
        private String startOrderDate;
        private String endOrderDate;
        private String modifiedByUsername;
        private String tenantOrderId;

        public Builder size(Integer size) { this.size = size; return this; }
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }
        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder status(List<String> status) { this.status = status; return this; }
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }
        public Builder assignedUser(String assignedUser) { this.assignedUser = assignedUser; return this; }
        public Builder searchTerm(String searchTerm) { this.searchTerm = searchTerm; return this; }
        public Builder channel(String channel) { this.channel = channel; return this; }
        public Builder sourceContainerCodes(List<String> sourceContainerCodes) { this.sourceContainerCodes = sourceContainerCodes; return this; }
        public Builder orderBy(String orderBy) { this.orderBy = orderBy; return this; }
        public Builder startTargetTime(String startTargetTime) { this.startTargetTime = startTargetTime; return this; }
        public Builder endTargetTime(String endTargetTime) { this.endTargetTime = endTargetTime; return this; }
        public Builder orderRef(String orderRef) { this.orderRef = orderRef; return this; }
        public Builder packJobIds(List<String> packJobIds) { this.packJobIds = packJobIds; return this; }
        public Builder processId(String processId) { this.processId = processId; return this; }
        public Builder pickJobRef(String pickJobRef) { this.pickJobRef = pickJobRef; return this; }
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }
        public Builder articleTitle(String articleTitle) { this.articleTitle = articleTitle; return this; }
        public Builder startOrderDate(String startOrderDate) { this.startOrderDate = startOrderDate; return this; }
        public Builder endOrderDate(String endOrderDate) { this.endOrderDate = endOrderDate; return this; }
        public Builder modifiedByUsername(String modifiedByUsername) { this.modifiedByUsername = modifiedByUsername; return this; }
        public Builder tenantOrderId(String tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }

        public PackJobListRequest build() { return new PackJobListRequest(this); }
    }
}
