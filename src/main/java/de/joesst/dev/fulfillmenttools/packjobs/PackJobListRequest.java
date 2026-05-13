package de.joesst.dev.fulfillmenttools.packjobs;

import de.joesst.dev.fulfillmenttools.id.TenantOrderId;

import java.util.List;

/**
 * Request parameters for listing pack jobs with optional filtering and pagination.
 *
 * <p>Use the builder to construct a request with desired filters and pagination settings,
 * then pass it to {@code PackingClient.list()} or {@code listAsync()}.
 *
 * <p>All filter fields are optional. When {@code null}, they do not constrain results.
 */
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
    private final TenantOrderId tenantOrderId;

    /**
     * @param size Maximum number of results to return; defaults to 100.
     * @param startAfterId Pagination cursor: return results after this pack job ID.
     * @param facilityRef Filter by facility reference.
     * @param status Filter by pack job status (one or more).
     * @param anonymized Filter by anonymization status.
     * @param assignedUser Filter by assigned user ID.
     * @param searchTerm General text search across order metadata.
     * @param channel Filter by delivery channel (e.g. {@code SHIPPING}, {@code COLLECT}).
     * @param sourceContainerCodes Filter by source container codes.
     * @param orderBy Sort key and direction (e.g. {@code "created,desc"}).
     * @param startTargetTime Filter by target time (inclusive start).
     * @param endTargetTime Filter by target time (inclusive end).
     * @param orderRef Filter by order reference.
     * @param packJobIds Filter by pack job ID (one or more).
     * @param processId Filter by process ID.
     * @param pickJobRef Filter by pick job reference.
     * @param shortId Filter by short ID.
     * @param articleTitle Filter by article title.
     * @param startOrderDate Filter by order date (inclusive start).
     * @param endOrderDate Filter by order date (inclusive end).
     * @param modifiedByUsername Filter by the user who last modified the pack job.
     * @param tenantOrderId Filter by tenant order ID.
     */
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
     * Returns the anonymization status filter.
     *
     * @return the anonymization status, or null if not set
     */
    public Boolean anonymized() { return anonymized; }

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
     * Returns the source container code filters.
     *
     * @return the source container codes list, or null if not set
     */
    public List<String> sourceContainerCodes() { return sourceContainerCodes; }

    /**
     * Returns the sort key and direction.
     *
     * @return the order by clause, or null if not set
     */
    public String orderBy() { return orderBy; }

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
     * Returns the order reference filter.
     *
     * @return the order reference, or null if not set
     */
    public String orderRef() { return orderRef; }

    /**
     * Returns the pack job ID filters.
     *
     * @return the pack job IDs list, or null if not set
     */
    public List<String> packJobIds() { return packJobIds; }

    /**
     * Returns the process ID filter.
     *
     * @return the process ID, or null if not set
     */
    public String processId() { return processId; }

    /**
     * Returns the pick job reference filter.
     *
     * @return the pick job reference, or null if not set
     */
    public String pickJobRef() { return pickJobRef; }

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
     * Returns the username of the last modifier.
     *
     * @return the modified by username, or null if not set
     */
    public String modifiedByUsername() { return modifiedByUsername; }

    /**
     * Returns the tenant order ID filter.
     *
     * @return the tenant order ID, or null if not set
     */
    public TenantOrderId tenantOrderId() { return tenantOrderId; }

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

    /**
     * Returns a new builder for constructing {@code PackJobListRequest} instances.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@code PackJobListRequest}.
     *
     * <p>Allows fluent construction of pack job list requests with optional filters
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
        private TenantOrderId tenantOrderId;

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
         * Sets the anonymization status filter.
         *
         * @param anonymized the anonymization status
         * @return this builder
         */
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }

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
         * Sets the source container code filters.
         *
         * @param sourceContainerCodes the source container codes
         * @return this builder
         */
        public Builder sourceContainerCodes(List<String> sourceContainerCodes) { this.sourceContainerCodes = sourceContainerCodes; return this; }

        /**
         * Sets the sort key and direction.
         *
         * @param orderBy the sort key and direction (e.g. {@code "created,desc"})
         * @return this builder
         */
        public Builder orderBy(String orderBy) { this.orderBy = orderBy; return this; }

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
         * Sets the order reference filter.
         *
         * @param orderRef the order reference
         * @return this builder
         */
        public Builder orderRef(String orderRef) { this.orderRef = orderRef; return this; }

        /**
         * Sets the pack job ID filters.
         *
         * @param packJobIds the pack job IDs
         * @return this builder
         */
        public Builder packJobIds(List<String> packJobIds) { this.packJobIds = packJobIds; return this; }

        /**
         * Sets the process ID filter.
         *
         * @param processId the process ID
         * @return this builder
         */
        public Builder processId(String processId) { this.processId = processId; return this; }

        /**
         * Sets the pick job reference filter.
         *
         * @param pickJobRef the pick job reference
         * @return this builder
         */
        public Builder pickJobRef(String pickJobRef) { this.pickJobRef = pickJobRef; return this; }

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
         * Sets the username of the last modifier filter.
         *
         * @param modifiedByUsername the username of the last modifier
         * @return this builder
         */
        public Builder modifiedByUsername(String modifiedByUsername) { this.modifiedByUsername = modifiedByUsername; return this; }

        /**
         * Sets the tenant order ID filter.
         *
         * @param tenantOrderId the tenant order ID
         * @return this builder
         */
        public Builder tenantOrderId(TenantOrderId tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }

        /**
         * Builds the {@code PackJobListRequest}.
         *
         * @return the constructed request
         */
        public PackJobListRequest build() { return new PackJobListRequest(this); }
    }
}
