package de.joesst.dev.fulfillmenttools.returns;

import java.util.List;

/**
 * Request to list returns with optional filtering and pagination.
 */
public final class ReturnListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String facilityId;
    private final List<String> itemReturnJobStatus;
    private final List<String> itemReturnStatus;
    private final List<String> itemReturnJobScannableCodes;
    private final List<String> itemReturnScannableCodes;
    private final String searchTerm;
    private final Boolean anonymized;

    private ReturnListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.facilityId = builder.facilityId;
        this.itemReturnJobStatus = builder.itemReturnJobStatus;
        this.itemReturnStatus = builder.itemReturnStatus;
        this.itemReturnJobScannableCodes = builder.itemReturnJobScannableCodes;
        this.itemReturnScannableCodes = builder.itemReturnScannableCodes;
        this.searchTerm = builder.searchTerm;
        this.anonymized = builder.anonymized;
    }

    /**
     * Returns the page size.
     *
     * @return the size, or null if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the cursor for pagination.
     *
     * @return the start after ID, or null if not set
     */
    public String startAfterId() { return startAfterId; }

    /**
     * Returns the facility ID filter.
     *
     * @return the facility ID, or null if not set
     */
    public String facilityId() { return facilityId; }

    /**
     * Returns the item return job status filter.
     *
     * @return the status list, or null if not set
     */
    public List<String> itemReturnJobStatus() { return itemReturnJobStatus; }

    /**
     * Returns the item return status filter.
     *
     * @return the status list, or null if not set
     */
    public List<String> itemReturnStatus() { return itemReturnStatus; }

    /**
     * Returns the item return job scannable codes filter.
     *
     * @return the scannable codes list, or null if not set
     */
    public List<String> itemReturnJobScannableCodes() { return itemReturnJobScannableCodes; }

    /**
     * Returns the item return scannable codes filter.
     *
     * @return the scannable codes list, or null if not set
     */
    public List<String> itemReturnScannableCodes() { return itemReturnScannableCodes; }

    /**
     * Returns the search term filter.
     *
     * @return the search term, or null if not set
     */
    public String searchTerm() { return searchTerm; }

    /**
     * Returns the anonymized flag filter.
     *
     * @return whether to filter by anonymized items, or null if not set
     */
    public Boolean anonymized() { return anonymized; }

    /**
     * Creates a new builder with the same state as this request.
     *
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.facilityId = this.facilityId;
        b.itemReturnJobStatus = this.itemReturnJobStatus;
        b.itemReturnStatus = this.itemReturnStatus;
        b.itemReturnJobScannableCodes = this.itemReturnJobScannableCodes;
        b.itemReturnScannableCodes = this.itemReturnScannableCodes;
        b.searchTerm = this.searchTerm;
        b.anonymized = this.anonymized;
        return b;
    }

    /**
     * Creates a new builder for constructing a {@code ReturnListRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link ReturnListRequest}.
     */
    public static final class Builder {

        private Integer size;
        private String startAfterId;

        /**
         * Creates a new Builder.
         */
        public Builder() {}
        private String facilityId;
        private List<String> itemReturnJobStatus;
        private List<String> itemReturnStatus;
        private List<String> itemReturnJobScannableCodes;
        private List<String> itemReturnScannableCodes;
        private String searchTerm;
        private Boolean anonymized;

        /**
         * Sets the page size.
         *
         * @param size the size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the pagination cursor.
         *
         * @param startAfterId the cursor
         * @return this builder
         */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /**
         * Sets the facility ID filter.
         *
         * @param facilityId the facility ID
         * @return this builder
         */
        public Builder facilityId(String facilityId) { this.facilityId = facilityId; return this; }

        /**
         * Sets the item return job status filter.
         *
         * @param itemReturnJobStatus the status list
         * @return this builder
         */
        public Builder itemReturnJobStatus(List<String> itemReturnJobStatus) { this.itemReturnJobStatus = itemReturnJobStatus; return this; }

        /**
         * Sets the item return status filter.
         *
         * @param itemReturnStatus the status list
         * @return this builder
         */
        public Builder itemReturnStatus(List<String> itemReturnStatus) { this.itemReturnStatus = itemReturnStatus; return this; }

        /**
         * Sets the item return job scannable codes filter.
         *
         * @param itemReturnJobScannableCodes the scannable codes list
         * @return this builder
         */
        public Builder itemReturnJobScannableCodes(List<String> itemReturnJobScannableCodes) { this.itemReturnJobScannableCodes = itemReturnJobScannableCodes; return this; }

        /**
         * Sets the item return scannable codes filter.
         *
         * @param itemReturnScannableCodes the scannable codes list
         * @return this builder
         */
        public Builder itemReturnScannableCodes(List<String> itemReturnScannableCodes) { this.itemReturnScannableCodes = itemReturnScannableCodes; return this; }

        /**
         * Sets the search term filter.
         *
         * @param searchTerm the search term
         * @return this builder
         */
        public Builder searchTerm(String searchTerm) { this.searchTerm = searchTerm; return this; }

        /**
         * Sets the anonymized flag filter.
         *
         * @param anonymized whether to filter by anonymized items
         * @return this builder
         */
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }

        /**
         * Builds the {@link ReturnListRequest}.
         *
         * @return a new request
         */
        public ReturnListRequest build() { return new ReturnListRequest(this); }
    }
}
