package de.joesst.dev.fulfillmenttools.inbound;

import java.util.List;

/**
 * Request parameters for listing stow jobs via {@link InboundClient#list(StowJobListRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * StowJobListRequest request = StowJobListRequest.builder()
 *     .size(50)
 *     .status(List.of("OPEN"))
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class StowJobListRequest {

    private final Integer size;
    private final String startAfterId;
    private final String sort;
    private final List<String> facilityRef;
    private final List<String> status;
    private final List<String> tenantArticleId;
    private final List<String> locationRef;
    private final List<String> stockRef;
    private final List<String> shortId;
    private final List<Integer> priority;

    private StowJobListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.sort = builder.sort;
        this.facilityRef = builder.facilityRef;
        this.status = builder.status;
        this.tenantArticleId = builder.tenantArticleId;
        this.locationRef = builder.locationRef;
        this.stockRef = builder.stockRef;
        this.shortId = builder.shortId;
        this.priority = builder.priority;
    }

    /**
     * Returns the maximum number of stow jobs to return per page.
     *
     * @return the page size, or {@code null} if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the cursor for pagination, indicating where to start fetching results.
     *
     * @return the start-after ID, or {@code null} if not set
     */
    public String startAfterId() { return startAfterId; }

    /**
     * Returns the sort order for results (e.g. {@code -created} for descending by created time).
     *
     * @return the sort specification, or {@code null} if not set
     */
    public String sort() { return sort; }

    /**
     * Returns the facility references to filter by.
     *
     * @return the facility reference list, or {@code null} if not set
     */
    public List<String> facilityRef() { return facilityRef; }

    /**
     * Returns the stow job statuses to filter by.
     *
     * @return the status list, or {@code null} if not set
     */
    public List<String> status() { return status; }

    /**
     * Returns the tenant article IDs to filter by.
     *
     * @return the tenant article ID list, or {@code null} if not set
     */
    public List<String> tenantArticleId() { return tenantArticleId; }

    /**
     * Returns the storage location references to filter by.
     *
     * @return the location reference list, or {@code null} if not set
     */
    public List<String> locationRef() { return locationRef; }

    /**
     * Returns the stock references to filter by.
     *
     * @return the stock reference list, or {@code null} if not set
     */
    public List<String> stockRef() { return stockRef; }

    /**
     * Returns the short IDs to filter by.
     *
     * @return the short ID list, or {@code null} if not set
     */
    public List<String> shortId() { return shortId; }

    /**
     * Returns the priority levels to filter by.
     *
     * @return the priority list, or {@code null} if not set
     */
    public List<Integer> priority() { return priority; }

    /**
     * Returns a new builder initialized with the current values of this request.
     *
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.sort = this.sort;
        b.facilityRef = this.facilityRef;
        b.status = this.status;
        b.tenantArticleId = this.tenantArticleId;
        b.locationRef = this.locationRef;
        b.stockRef = this.stockRef;
        b.shortId = this.shortId;
        b.priority = this.priority;
        return b;
    }

    /**
     * Returns a new {@link Builder} for constructing a {@code StowJobListRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link StowJobListRequest}.
     */
    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private String sort;
        private List<String> facilityRef;
        private List<String> status;
        private List<String> tenantArticleId;
        private List<String> locationRef;
        private List<String> stockRef;
        private List<String> shortId;
        private List<Integer> priority;

        /** Creates a new Builder instance. */
        public Builder() {}

        /**
         * Sets the maximum number of stow jobs to return per page.
         *
         * @param size the page size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the cursor for pagination, indicating where to start fetching results.
         *
         * @param startAfterId the start-after ID
         * @return this builder
         */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /**
         * Sets the sort order for results (e.g. {@code -created} for descending by created time).
         *
         * @param sort the sort specification
         * @return this builder
         */
        public Builder sort(String sort) { this.sort = sort; return this; }

        /**
         * Sets the facility references to filter by.
         *
         * @param facilityRef the facility reference list
         * @return this builder
         */
        public Builder facilityRef(List<String> facilityRef) { this.facilityRef = facilityRef; return this; }

        /**
         * Sets the stow job statuses to filter by.
         *
         * @param status the status list
         * @return this builder
         */
        public Builder status(List<String> status) { this.status = status; return this; }

        /**
         * Sets the tenant article IDs to filter by.
         *
         * @param tenantArticleId the tenant article ID list
         * @return this builder
         */
        public Builder tenantArticleId(List<String> tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }

        /**
         * Sets the storage location references to filter by.
         *
         * @param locationRef the location reference list
         * @return this builder
         */
        public Builder locationRef(List<String> locationRef) { this.locationRef = locationRef; return this; }

        /**
         * Sets the stock references to filter by.
         *
         * @param stockRef the stock reference list
         * @return this builder
         */
        public Builder stockRef(List<String> stockRef) { this.stockRef = stockRef; return this; }

        /**
         * Sets the short IDs to filter by.
         *
         * @param shortId the short ID list
         * @return this builder
         */
        public Builder shortId(List<String> shortId) { this.shortId = shortId; return this; }

        /**
         * Sets the priority levels to filter by.
         *
         * @param priority the priority list
         * @return this builder
         */
        public Builder priority(List<Integer> priority) { this.priority = priority; return this; }

        /**
         * Builds the {@link StowJobListRequest}.
         *
         * @return a new {@code StowJobListRequest}
         */
        public StowJobListRequest build() { return new StowJobListRequest(this); }
    }
}
