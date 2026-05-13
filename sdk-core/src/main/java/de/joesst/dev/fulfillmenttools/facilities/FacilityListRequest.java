package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;

import java.util.List;

/**
 * Request payload for listing facilities with optional filtering and pagination.
 *
 * <p>Use {@link #builder()} to construct instances fluently.
 * All fields are optional.
 *
 * <p>Example:
 * <pre>{@code
 * var request = FacilityListRequest.builder()
 *     .status(Arrays.asList("ONLINE", "SUSPENDED"))
 *     .size(50)
 *     .startAfterId("last-facility-id")
 *     .build();
 * }</pre>
 */
public final class FacilityListRequest {

    private final Integer size;
    private final String startAfterId;
    private final List<String> status;
    private final TenantFacilityId tenantFacilityId;
    private final List<String> type;
    private final String orderBy;

    private FacilityListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.status = builder.status;
        this.tenantFacilityId = builder.tenantFacilityId;
        this.type = builder.type;
        this.orderBy = builder.orderBy;
    }

    /**
     * Returns the page size limit.
     * @return the size limit, or {@code null} if not set
     */
    public Integer size() { return size; }

    /**
     * Returns the cursor for pagination.
     * @return the start-after ID, or {@code null} if not set
     */
    public String startAfterId() { return startAfterId; }

    /**
     * Returns the facility status filter.
     * @return the status list, or {@code null} if not set
     */
    public List<String> status() { return status; }

    /**
     * Returns the tenant facility ID filter.
     * @return the tenant facility ID, or {@code null} if not set
     */
    public TenantFacilityId tenantFacilityId() { return tenantFacilityId; }

    /**
     * Returns the facility type filter.
     * @return the type list, or {@code null} if not set
     */
    public List<String> type() { return type; }

    /**
     * Returns the order-by field.
     * @return the order-by field, or {@code null} if not set
     */
    public String orderBy() { return orderBy; }

    /**
     * Returns a new builder initialized with this request's current values.
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.status = this.status;
        b.tenantFacilityId = this.tenantFacilityId;
        b.type = this.type;
        b.orderBy = this.orderBy;
        return b;
    }

    /**
     * Returns a new builder for constructing a {@code FacilityListRequest}.
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /** Builder for {@code FacilityListRequest}. */
    public static final class Builder {

        private Integer size;
        private String startAfterId;
        private List<String> status;
        private TenantFacilityId tenantFacilityId;
        private List<String> type;
        private String orderBy;

        /** Creates a new Builder instance. */
        public Builder() {}

        /**
         * Sets the page size limit.
         * @param size the page size
         * @return this builder
         */
        public Builder size(Integer size) { this.size = size; return this; }

        /**
         * Sets the cursor for pagination.
         * @param startAfterId the start-after ID
         * @return this builder
         */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /**
         * Sets the facility status filter.
         * @param status the status list
         * @return this builder
         */
        public Builder status(List<String> status) { this.status = status; return this; }

        /**
         * Sets the tenant facility ID filter.
         * @param tenantFacilityId the tenant facility ID
         * @return this builder
         */
        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }

        /**
         * Sets the facility type filter.
         * @param type the type list
         * @return this builder
         */
        public Builder type(List<String> type) { this.type = type; return this; }

        /**
         * Sets the order-by field.
         * @param orderBy the order-by field
         * @return this builder
         */
        public Builder orderBy(String orderBy) { this.orderBy = orderBy; return this; }

        /**
         * Builds and returns a new {@code FacilityListRequest}.
         * @return the built request
         */
        public FacilityListRequest build() { return new FacilityListRequest(this); }
    }
}
