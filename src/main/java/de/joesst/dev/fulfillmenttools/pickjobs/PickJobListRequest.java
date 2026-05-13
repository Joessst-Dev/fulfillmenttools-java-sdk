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
     * Returns the maximum number of results to return.
     *
     * @return the page size, or null if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the pagination cursor ID.
     *
     * @return the start after ID, or null if not set
     */
    public String startAfterId() { return startAfterId; }

    /**
     * Returns the facility reference filter.
     *
     * @return the facility reference, or null if not set
     */
    public String facilityRef() { return facilityRef; }

    /**
     * Returns the status filter values.
     *
     * @return the status list, or null if not set
     */
    public List<String> status() { return status; }

    /**
     * Returns the order reference filter.
     *
     * @return the order reference, or null if not set
     */
    public String orderRef() { return orderRef; }

    /**
     * Returns the tenant order ID filter.
     *
     * @return the tenant order ID, or null if not set
     */
    public String tenantOrderId() { return tenantOrderId; }

    /**
     * Returns the assigned user ID filter.
     *
     * @return the assigned user ID, or null if not set
     */
    public String assignedUser() { return assignedUser; }

    /**
     * Returns the text search filter.
     *
     * @return the search term, or null if not set
     */
    public String searchTerm() { return searchTerm; }

    /**
     * Returns the delivery channel filter.
     *
     * @return the channel, or null if not set
     */
    public String channel() { return channel; }

    /**
     * Returns the consumer name filter.
     *
     * @return the consumer name, or null if not set
     */
    public String consumerName() { return consumerName; }

    /**
     * Returns the short ID filter.
     *
     * @return the short ID, or null if not set
     */
    public String shortId() { return shortId; }

    /**
     * Returns the article title filter.
     *
     * @return the article title, or null if not set
     */
    public String articleTitle() { return articleTitle; }

    /**
     * Returns the anonymization status filter.
     *
     * @return the anonymization status, or null if not set
     */
    public Boolean anonymized() { return anonymized; }

    /**
     * Returns the sort key and direction.
     *
     * @return the order by clause, or null if not set
     */
    public String orderBy() { return orderBy; }

    /**
     * Returns the username of the last modifier.
     *
     * @return the modified by username, or null if not set
     */
    public String modifiedByUsername() { return modifiedByUsername; }

    /**
     * Returns the order date start filter.
     *
     * @return the start order date, or null if not set
     */
    public String startOrderDate() { return startOrderDate; }

    /**
     * Returns the order date end filter.
     *
     * @return the end order date, or null if not set
     */
    public String endOrderDate() { return endOrderDate; }

    /**
     * Returns the target time start filter.
     *
     * @return the start target time, or null if not set
     */
    public String startTargetTime() { return startTargetTime; }

    /**
     * Returns the target time end filter.
     *
     * @return the end target time, or null if not set
     */
    public String endTargetTime() { return endTargetTime; }

    /**
     * Returns the carrier key filters.
     *
     * @return the carrier keys list, or null if not set
     */
    public List<String> carrierKeys() { return carrierKeys; }

    /**
     * Returns the zone reference filters.
     *
     * @return the zone references list, or null if not set
     */
    public List<String> zoneRefs() { return zoneRefs; }

    /**
     * Returns the pick job reference filters.
     *
     * @return the pick job references list, or null if not set
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
         * Sets the page size.
         *
         * @param size the maximum number of results to return
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the pagination cursor ID.
         *
         * @param startAfterId the cursor ID
         * @return this builder
         */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /**
         * Sets the facility reference filter.
         *
         * @param facilityRef the facility reference
         * @return this builder
         */
        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }

        /**
         * Sets the status filter.
         *
         * @param status the status values
         * @return this builder
         */
        public Builder status(List<String> status) { this.status = status; return this; }

        /**
         * Sets the order reference filter.
         *
         * @param orderRef the order reference
         * @return this builder
         */
        public Builder orderRef(String orderRef) { this.orderRef = orderRef; return this; }

        /**
         * Sets the tenant order ID filter.
         *
         * @param tenantOrderId the tenant order ID
         * @return this builder
         */
        public Builder tenantOrderId(String tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }

        /**
         * Sets the assigned user ID filter.
         *
         * @param assignedUser the assigned user ID
         * @return this builder
         */
        public Builder assignedUser(String assignedUser) { this.assignedUser = assignedUser; return this; }

        /**
         * Sets the text search filter.
         *
         * @param searchTerm the search term
         * @return this builder
         */
        public Builder searchTerm(String searchTerm) { this.searchTerm = searchTerm; return this; }

        /**
         * Sets the delivery channel filter.
         *
         * @param channel the delivery channel
         * @return this builder
         */
        public Builder channel(String channel) { this.channel = channel; return this; }

        /**
         * Sets the consumer name filter.
         *
         * @param consumerName the consumer name
         * @return this builder
         */
        public Builder consumerName(String consumerName) { this.consumerName = consumerName; return this; }

        /**
         * Sets the short ID filter.
         *
         * @param shortId the short ID
         * @return this builder
         */
        public Builder shortId(String shortId) { this.shortId = shortId; return this; }

        /**
         * Sets the article title filter.
         *
         * @param articleTitle the article title
         * @return this builder
         */
        public Builder articleTitle(String articleTitle) { this.articleTitle = articleTitle; return this; }

        /**
         * Sets the anonymization status filter.
         *
         * @param anonymized the anonymization status
         * @return this builder
         */
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }

        /**
         * Sets the sort key and direction.
         *
         * @param orderBy the sort key and direction (e.g. {@code "created,desc"})
         * @return this builder
         */
        public Builder orderBy(String orderBy) { this.orderBy = orderBy; return this; }

        /**
         * Sets the username of the last modifier filter.
         *
         * @param modifiedByUsername the username of the last modifier
         * @return this builder
         */
        public Builder modifiedByUsername(String modifiedByUsername) { this.modifiedByUsername = modifiedByUsername; return this; }

        /**
         * Sets the order date start filter.
         *
         * @param startOrderDate the order date start (inclusive)
         * @return this builder
         */
        public Builder startOrderDate(String startOrderDate) { this.startOrderDate = startOrderDate; return this; }

        /**
         * Sets the order date end filter.
         *
         * @param endOrderDate the order date end (inclusive)
         * @return this builder
         */
        public Builder endOrderDate(String endOrderDate) { this.endOrderDate = endOrderDate; return this; }

        /**
         * Sets the target time start filter.
         *
         * @param startTargetTime the target time start (inclusive)
         * @return this builder
         */
        public Builder startTargetTime(String startTargetTime) { this.startTargetTime = startTargetTime; return this; }

        /**
         * Sets the target time end filter.
         *
         * @param endTargetTime the target time end (inclusive)
         * @return this builder
         */
        public Builder endTargetTime(String endTargetTime) { this.endTargetTime = endTargetTime; return this; }

        /**
         * Sets the carrier key filters.
         *
         * @param carrierKeys the carrier keys
         * @return this builder
         */
        public Builder carrierKeys(List<String> carrierKeys) { this.carrierKeys = carrierKeys; return this; }

        /**
         * Sets the zone reference filters.
         *
         * @param zoneRefs the zone references
         * @return this builder
         */
        public Builder zoneRefs(List<String> zoneRefs) { this.zoneRefs = zoneRefs; return this; }

        /**
         * Sets the pick job reference filters.
         *
         * @param pickJobRefs the pick job references
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
