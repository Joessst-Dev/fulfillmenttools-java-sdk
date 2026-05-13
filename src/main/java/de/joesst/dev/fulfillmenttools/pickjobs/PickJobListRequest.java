package de.joesst.dev.fulfillmenttools.pickjobs;

import java.util.List;

/**
 * Request parameters for listing pick jobs with optional filtering and pagination.
 *
 * <p>Use the builder to construct a request with desired filters and pagination settings,
 * then pass it to {@code PickJobsClient.list()} or {@code listAsync()}.
 *
 * <p>All filter fields are optional. When {@code null}, they do not constrain results.
 */
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

    /**
     * @param size Maximum number of results to return; defaults to 100.
     * @param startAfterId Pagination cursor: return results after this pick job ID.
     * @param facilityRef Filter by facility reference.
     * @param status Filter by pick job status (one or more).
     * @param orderRef Filter by order reference.
     * @param tenantOrderId Filter by tenant order ID.
     * @param assignedUser Filter by assigned user ID.
     * @param searchTerm General text search across order metadata.
     * @param channel Filter by delivery channel (e.g. {@code SHIPPING}, {@code COLLECT}).
     * @param consumerName Filter by consumer name.
     * @param shortId Filter by short ID.
     * @param articleTitle Filter by article title.
     * @param anonymized Filter by anonymization status.
     * @param orderBy Sort key and direction (e.g. {@code "created,desc"}).
     * @param modifiedByUsername Filter by the user who last modified the pick job.
     * @param startOrderDate Filter by order date (inclusive start).
     * @param endOrderDate Filter by order date (inclusive end).
     * @param startTargetTime Filter by target time (inclusive start).
     * @param endTargetTime Filter by target time (inclusive end).
     * @param carrierKeys Filter by carrier key (one or more).
     * @param zoneRefs Filter by zone reference (one or more).
     * @param pickJobRefs Filter by pick job reference (one or more).
     */
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

    /**
     * @return Maximum number of results to return.
     */
    public Integer size() { return size; }

    /**
     * @return Pagination cursor ID.
     */
    public String startAfterId() { return startAfterId; }

    /**
     * @return Facility reference filter.
     */
    public String facilityRef() { return facilityRef; }

    /**
     * @return Status filter values.
     */
    public List<String> status() { return status; }

    /**
     * @return Order reference filter.
     */
    public String orderRef() { return orderRef; }

    /**
     * @return Tenant order ID filter.
     */
    public String tenantOrderId() { return tenantOrderId; }

    /**
     * @return Assigned user ID filter.
     */
    public String assignedUser() { return assignedUser; }

    /**
     * @return Text search filter.
     */
    public String searchTerm() { return searchTerm; }

    /**
     * @return Delivery channel filter.
     */
    public String channel() { return channel; }

    /**
     * @return Consumer name filter.
     */
    public String consumerName() { return consumerName; }

    /**
     * @return Short ID filter.
     */
    public String shortId() { return shortId; }

    /**
     * @return Article title filter.
     */
    public String articleTitle() { return articleTitle; }

    /**
     * @return Anonymization status filter.
     */
    public Boolean anonymized() { return anonymized; }

    /**
     * @return Sort key and direction.
     */
    public String orderBy() { return orderBy; }

    /**
     * @return Username of the last modifier.
     */
    public String modifiedByUsername() { return modifiedByUsername; }

    /**
     * @return Order date start filter.
     */
    public String startOrderDate() { return startOrderDate; }

    /**
     * @return Order date end filter.
     */
    public String endOrderDate() { return endOrderDate; }

    /**
     * @return Target time start filter.
     */
    public String startTargetTime() { return startTargetTime; }

    /**
     * @return Target time end filter.
     */
    public String endTargetTime() { return endTargetTime; }

    /**
     * @return Carrier key filters.
     */
    public List<String> carrierKeys() { return carrierKeys; }

    /**
     * @return Zone reference filters.
     */
    public List<String> zoneRefs() { return zoneRefs; }

    /**
     * @return Pick job reference filters.
     */
    public List<String> pickJobRefs() { return pickJobRefs; }

    /**
     * Returns a new builder initialized with this request's current values.
     *
     * @return a new builder
     */
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

    /**
     * Returns a new builder for constructing {@code PickJobListRequest} instances.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@code PickJobListRequest}.
     *
     * <p>Allows fluent construction of pick job list requests with optional filters
     * and pagination parameters. All filters are optional; {@code null} filters are
     * not applied to the query.
     */
    public static final class Builder {

        /** Creates a new Builder. */
        public Builder() {}

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

        /**
         * @param size Maximum number of results to return.
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * @param startAfterId Pagination cursor ID.
         * @return this builder
         */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /**
         * @param facilityRef Facility reference to filter by.
         * @return this builder
         */
        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }

        /**
         * @param status Status values to filter by.
         * @return this builder
         */
        public Builder status(List<String> status) { this.status = status; return this; }

        /**
         * @param orderRef Order reference to filter by.
         * @return this builder
         */
        public Builder orderRef(String orderRef) { this.orderRef = orderRef; return this; }

        /**
         * @param tenantOrderId Tenant order ID to filter by.
         * @return this builder
         */
        public Builder tenantOrderId(String tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }

        /**
         * @param assignedUser Assigned user ID to filter by.
         * @return this builder
         */
        public Builder assignedUser(String assignedUser) { this.assignedUser = assignedUser; return this; }

        /**
         * @param searchTerm Text search term.
         * @return this builder
         */
        public Builder searchTerm(String searchTerm) { this.searchTerm = searchTerm; return this; }

        /**
         * @param channel Delivery channel to filter by.
         * @return this builder
         */
        public Builder channel(String channel) { this.channel = channel; return this; }

        /**
         * @param consumerName Consumer name to filter by.
         * @return this builder
         */
        public Builder consumerName(String consumerName) { this.consumerName = consumerName; return this; }

        /**
         * @param shortId Short ID to filter by.
         * @return this builder
         */
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }

        /**
         * @param articleTitle Article title to filter by.
         * @return this builder
         */
        public Builder articleTitle(String articleTitle) { this.articleTitle = articleTitle; return this; }

        /**
         * @param anonymized Anonymization status to filter by.
         * @return this builder
         */
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }

        /**
         * @param orderBy Sort key and direction (e.g. {@code "created,desc"}).
         * @return this builder
         */
        public Builder orderBy(String orderBy) { this.orderBy = orderBy; return this; }

        /**
         * @param modifiedByUsername Username of the last modifier to filter by.
         * @return this builder
         */
        public Builder modifiedByUsername(String modifiedByUsername) { this.modifiedByUsername = modifiedByUsername; return this; }

        /**
         * @param startOrderDate Order date start (inclusive) to filter by.
         * @return this builder
         */
        public Builder startOrderDate(String startOrderDate) { this.startOrderDate = startOrderDate; return this; }

        /**
         * @param endOrderDate Order date end (inclusive) to filter by.
         * @return this builder
         */
        public Builder endOrderDate(String endOrderDate) { this.endOrderDate = endOrderDate; return this; }

        /**
         * @param startTargetTime Target time start (inclusive) to filter by.
         * @return this builder
         */
        public Builder startTargetTime(String startTargetTime) { this.startTargetTime = startTargetTime; return this; }

        /**
         * @param endTargetTime Target time end (inclusive) to filter by.
         * @return this builder
         */
        public Builder endTargetTime(String endTargetTime) { this.endTargetTime = endTargetTime; return this; }

        /**
         * @param carrierKeys Carrier keys to filter by.
         * @return this builder
         */
        public Builder carrierKeys(List<String> carrierKeys) { this.carrierKeys = carrierKeys; return this; }

        /**
         * @param zoneRefs Zone references to filter by.
         * @return this builder
         */
        public Builder zoneRefs(List<String> zoneRefs) { this.zoneRefs = zoneRefs; return this; }

        /**
         * @param pickJobRefs Pick job references to filter by.
         * @return this builder
         */
        public Builder pickJobRefs(List<String> pickJobRefs) { this.pickJobRefs = pickJobRefs; return this; }

        /**
         * Builds the {@code PickJobListRequest}.
         *
         * @return the constructed request
         */
        public PickJobListRequest build() { return new PickJobListRequest(this); }
    }
}
