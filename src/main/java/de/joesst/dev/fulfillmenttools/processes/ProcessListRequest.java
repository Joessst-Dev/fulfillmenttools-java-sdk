package de.joesst.dev.fulfillmenttools.processes;

import java.util.List;

/**
 * Request to list processes with optional filtering and pagination.
 */
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
     * Returns the facility references filter.
     *
     * @return the facility references list, or null if not set
     */
    public List<String> facilityRefs() { return facilityRefs; }

    /**
     * Returns the status filter.
     *
     * @return the status list, or null if not set
     */
    public List<String> status() { return status; }

    /**
     * Returns the operative status filter.
     *
     * @return the operative status list, or null if not set
     */
    public List<String> operativeStatus() { return operativeStatus; }

    /**
     * Returns the tenant order ID filter.
     *
     * @return the tenant order ID, or null if not set
     */
    public String tenantOrderId() { return tenantOrderId; }

    /**
     * Returns the search term filter.
     *
     * @return the search term, or null if not set
     */
    public String searchTerm() { return searchTerm; }

    /**
     * Creates a new builder with the same state as this request.
     *
     * @return a new builder
     */
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

    /**
     * Creates a new builder for constructing a {@code ProcessListRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link ProcessListRequest}.
     */
    public static final class Builder {

        /** Creates a new Builder. */
        public Builder() {}

        private Integer size;
        private String startAfterId;
        private List<String> facilityRefs;
        private List<String> status;
        private List<String> operativeStatus;
        private String tenantOrderId;
        private String searchTerm;

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
         * Sets the facility references filter.
         *
         * @param facilityRefs the facility references
         * @return this builder
         */
        public Builder facilityRefs(List<String> facilityRefs) { this.facilityRefs = facilityRefs; return this; }

        /**
         * Sets the status filter.
         *
         * @param status the status list
         * @return this builder
         */
        public Builder status(List<String> status) { this.status = status; return this; }

        /**
         * Sets the operative status filter.
         *
         * @param operativeStatus the operative status list
         * @return this builder
         */
        public Builder operativeStatus(List<String> operativeStatus) { this.operativeStatus = operativeStatus; return this; }

        /**
         * Sets the tenant order ID filter.
         *
         * @param tenantOrderId the tenant order ID
         * @return this builder
         */
        public Builder tenantOrderId(String tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }

        /**
         * Sets the search term filter.
         *
         * @param searchTerm the search term
         * @return this builder
         */
        public Builder searchTerm(String searchTerm) { this.searchTerm = searchTerm; return this; }

        /**
         * Builds the {@link ProcessListRequest}.
         *
         * @return a new request
         */
        public ProcessListRequest build() { return new ProcessListRequest(this); }
    }
}
