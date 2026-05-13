package de.joesst.dev.fulfillmenttools.handoverjobs;

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
    private final String tenantOrderId;
    private final String searchTerm;
    private final String startTargetTime;
    private final String endTargetTime;

    /**
     * @param size Maximum number of results to return; defaults to 100.
     * @param startAfterId Pagination cursor: return results after this handover job ID.
     * @param facilityRef Filter by facility reference.
     * @param status Filter by handover job status (one or more).
     * @param pickJobRef Filter by pick job reference.
     * @param shipmentRef Filter by shipment reference.
     * @param assignedUser Filter by assigned user ID.
     * @param carrierRefs Filter by carrier reference (one or more).
     * @param channel Filter by delivery channel (e.g. {@code DELIVERY}, {@code COLLECT}).
     * @param anonymized Filter by anonymization status.
     * @param tenantOrderId Filter by tenant order ID.
     * @param searchTerm General text search across order metadata.
     * @param startTargetTime Filter by target time (inclusive start).
     * @param endTargetTime Filter by target time (inclusive end).
     */
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
     * @return Pick job reference filter.
     */
    public String pickJobRef() { return pickJobRef; }

    /**
     * @return Shipment reference filter.
     */
    public String shipmentRef() { return shipmentRef; }

    /**
     * @return Assigned user ID filter.
     */
    public String assignedUser() { return assignedUser; }

    /**
     * @return Carrier reference filters.
     */
    public List<String> carrierRefs() { return carrierRefs; }

    /**
     * @return Delivery channel filter.
     */
    public String channel() { return channel; }

    /**
     * @return Anonymization status filter.
     */
    public Boolean anonymized() { return anonymized; }

    /**
     * @return Tenant order ID filter.
     */
    public String tenantOrderId() { return tenantOrderId; }

    /**
     * @return Text search filter.
     */
    public String searchTerm() { return searchTerm; }

    /**
     * @return Target time start filter.
     */
    public String startTargetTime() { return startTargetTime; }

    /**
     * @return Target time end filter.
     */
    public String endTargetTime() { return endTargetTime; }

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
     *
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
        private String tenantOrderId;
        private String searchTerm;
        private String startTargetTime;
        private String endTargetTime;

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
         * @param pickJobRef Pick job reference to filter by.
         * @return this builder
         */
        public Builder pickJobRef(String pickJobRef) { this.pickJobRef = pickJobRef; return this; }

        /**
         * @param shipmentRef Shipment reference to filter by.
         * @return this builder
         */
        public Builder shipmentRef(String shipmentRef) { this.shipmentRef = shipmentRef; return this; }

        /**
         * @param assignedUser Assigned user ID to filter by.
         * @return this builder
         */
        public Builder assignedUser(String assignedUser) { this.assignedUser = assignedUser; return this; }

        /**
         * @param carrierRefs Carrier references to filter by.
         * @return this builder
         */
        public Builder carrierRefs(List<String> carrierRefs) { this.carrierRefs = carrierRefs; return this; }

        /**
         * @param channel Delivery channel to filter by.
         * @return this builder
         */
        public Builder channel(String channel) { this.channel = channel; return this; }

        /**
         * @param anonymized Anonymization status to filter by.
         * @return this builder
         */
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }

        /**
         * @param tenantOrderId Tenant order ID to filter by.
         * @return this builder
         */
        public Builder tenantOrderId(String tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }

        /**
         * @param searchTerm Text search term.
         * @return this builder
         */
        public Builder searchTerm(String searchTerm) { this.searchTerm = searchTerm; return this; }

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
         * Builds the {@code HandoverJobListRequest}.
         *
         * @return the constructed request
         */
        public HandoverJobListRequest build() { return new HandoverJobListRequest(this); }
    }
}
