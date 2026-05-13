package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.id.TenantOrderId;

import java.util.List;

/**
 * Request parameters for listing handover jobs with optional filtering and pagination.
 *
 * <p>Use the builder to construct a request with desired filters and pagination settings,
 * then pass it to {@code HandoverJobsClient.list()} or {@code listAsync()}.
 *
 * <p>All filter fields are optional. When {@code null}, they do not constrain results.
 */
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
    private final TenantOrderId tenantOrderId;
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

    /**
     * Returns the maximum number of results to return.
     * @return the page size, or {@code null} if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the pagination cursor ID.
     * @return the start-after ID, or {@code null} if not set
     */
    public String startAfterId() { return startAfterId; }

    /**
     * Returns the facility reference filter.
     * @return the facility reference, or {@code null} if not set
     */
    public String facilityRef() { return facilityRef; }

    /**
     * Returns the status filter values.
     * @return the status list, or {@code null} if not set
     */
    public List<String> status() { return status; }

    /**
     * Returns the pick job reference filter.
     * @return the pick job reference, or {@code null} if not set
     */
    public String pickJobRef() { return pickJobRef; }

    /**
     * Returns the shipment reference filter.
     * @return the shipment reference, or {@code null} if not set
     */
    public String shipmentRef() { return shipmentRef; }

    /**
     * Returns the assigned user ID filter.
     * @return the assigned user ID, or {@code null} if not set
     */
    public String assignedUser() { return assignedUser; }

    /**
     * Returns the carrier reference filters.
     * @return the carrier reference list, or {@code null} if not set
     */
    public List<String> carrierRefs() { return carrierRefs; }

    /**
     * Returns the delivery channel filter.
     * @return the channel, or {@code null} if not set
     */
    public String channel() { return channel; }

    /**
     * Returns the anonymization status filter.
     * @return the anonymized flag, or {@code null} if not set
     */
    public Boolean anonymized() { return anonymized; }

    /**
     * Returns the tenant order ID filter.
     * @return the tenant order ID, or {@code null} if not set
     */
    public TenantOrderId tenantOrderId() { return tenantOrderId; }

    /**
     * Returns the text search filter.
     * @return the search term, or {@code null} if not set
     */
    public String searchTerm() { return searchTerm; }

    /**
     * Returns the target time start filter (inclusive).
     * @return the start target time, or {@code null} if not set
     */
    public String startTargetTime() { return startTargetTime; }

    /**
     * Returns the target time end filter (inclusive).
     * @return the end target time, or {@code null} if not set
     */
    public String endTargetTime() { return endTargetTime; }

    /**
     * Returns a new builder initialized with this request's current values.
     * @return a new builder
     */
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

    /**
     * Returns a new builder for constructing {@code HandoverJobListRequest} instances.
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@code HandoverJobListRequest}.
     *
     * <p>Allows fluent construction of handover job list requests with optional filters
     * and pagination parameters. All filters are optional; {@code null} filters are
     * not applied to the query.
     */
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
        private TenantOrderId tenantOrderId;
        private String searchTerm;
        private String startTargetTime;
        private String endTargetTime;

        /** Creates a new Builder instance. */
        public Builder() {}

        /**
         * Sets the maximum number of results to return.
         * @param size the page size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the pagination cursor ID.
         * @param startAfterId the start-after ID
         * @return this builder
         */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /**
         * Sets the facility reference to filter by.
         * @param facilityRef the facility reference
         * @return this builder
         */
        public Builder facilityRef(String facilityRef) { this.facilityRef = facilityRef; return this; }

        /**
         * Sets the status values to filter by.
         * @param status the status list
         * @return this builder
         */
        public Builder status(List<String> status) { this.status = status; return this; }

        /**
         * Sets the pick job reference to filter by.
         * @param pickJobRef the pick job reference
         * @return this builder
         */
        public Builder pickJobRef(String pickJobRef) { this.pickJobRef = pickJobRef; return this; }

        /**
         * Sets the shipment reference to filter by.
         * @param shipmentRef the shipment reference
         * @return this builder
         */
        public Builder shipmentRef(String shipmentRef) { this.shipmentRef = shipmentRef; return this; }

        /**
         * Sets the assigned user ID to filter by.
         * @param assignedUser the assigned user ID
         * @return this builder
         */
        public Builder assignedUser(String assignedUser) { this.assignedUser = assignedUser; return this; }

        /**
         * Sets the carrier references to filter by.
         * @param carrierRefs the carrier reference list
         * @return this builder
         */
        public Builder carrierRefs(List<String> carrierRefs) { this.carrierRefs = carrierRefs; return this; }

        /**
         * Sets the delivery channel to filter by.
         * @param channel the delivery channel
         * @return this builder
         */
        public Builder channel(String channel) { this.channel = channel; return this; }

        /**
         * Sets the anonymization status to filter by.
         * @param anonymized the anonymized flag
         * @return this builder
         */
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }

        /**
         * Sets the tenant order ID to filter by.
         * @param tenantOrderId the tenant order ID
         * @return this builder
         */
        public Builder tenantOrderId(TenantOrderId tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }

        /**
         * Sets the text search term.
         * @param searchTerm the search term
         * @return this builder
         */
        public Builder searchTerm(String searchTerm) { this.searchTerm = searchTerm; return this; }

        /**
         * Sets the target time start filter (inclusive).
         * @param startTargetTime the start target time
         * @return this builder
         */
        public Builder startTargetTime(String startTargetTime) { this.startTargetTime = startTargetTime; return this; }

        /**
         * Sets the target time end filter (inclusive).
         * @param endTargetTime the end target time
         * @return this builder
         */
        public Builder endTargetTime(String endTargetTime) { this.endTargetTime = endTargetTime; return this; }

        /**
         * Builds the {@code HandoverJobListRequest}.
         * @return the constructed request
         */
        public HandoverJobListRequest build() { return new HandoverJobListRequest(this); }
    }
}
