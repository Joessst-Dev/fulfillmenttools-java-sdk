package de.joesst.dev.fulfillmenttools.stocks;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;

import java.util.List;

/**
 * Request parameters for listing stock entries via {@link StocksClient#list(StockListRequest)}.
 *
 * <p>Use the fluent {@link Builder} to construct instances:
 * <pre>{@code
 * StockListRequest request = StockListRequest.builder()
 *     .size(50)
 *     .facilityRef("fac-1")
 *     .build();
 * }</pre>
 *
 * <p>Thread-safety: immutable after construction; safe for concurrent use.
 */
public final class StockListRequest {

    private final Integer size;
    private final String startAfterId;
    private final FacilityId facilityRef;
    private final TenantFacilityId tenantFacilityId;
    private final List<String> tenantArticleId;
    private final List<String> locationRef;

    private StockListRequest(Builder builder) {
        this.size = builder.size;
        this.startAfterId = builder.startAfterId;
        this.facilityRef = builder.facilityRef;
        this.tenantFacilityId = builder.tenantFacilityId;
        this.tenantArticleId = builder.tenantArticleId;
        this.locationRef = builder.locationRef;
    }

    /**
     * Returns the maximum number of stock entries to return per page.
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
     * Returns the facility reference to filter by.
     *
     * @return the facility reference, or {@code null} if not set
     */
    public FacilityId facilityRef() { return facilityRef; }

    /**
     * Returns the tenant facility ID to filter by.
     *
     * @return the tenant facility ID, or {@code null} if not set
     */
    public TenantFacilityId tenantFacilityId() { return tenantFacilityId; }

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
     * Returns a new builder initialized with the current values of this request.
     *
     * @return a new builder
     */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.size = this.size;
        b.startAfterId = this.startAfterId;
        b.facilityRef = this.facilityRef;
        b.tenantFacilityId = this.tenantFacilityId;
        b.tenantArticleId = this.tenantArticleId;
        b.locationRef = this.locationRef;
        return b;
    }

    /**
     * Returns a new {@link Builder} for constructing a {@code StockListRequest}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Fluent builder for {@link StockListRequest}.
     */
    public static final class Builder {

        /**
         * Creates a new Builder.
         */
        public Builder() {}

        private Integer size;
        private String startAfterId;
        private FacilityId facilityRef;
        private TenantFacilityId tenantFacilityId;
        private List<String> tenantArticleId;
        private List<String> locationRef;

        /**
         * Sets the maximum number of stock entries to return per page.
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
         * Sets the facility reference to filter by.
         *
         * @param facilityRef the facility reference
         * @return this builder
         */
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }

        /**
         * Sets the tenant facility ID to filter by.
         *
         * @param tenantFacilityId the tenant facility ID
         * @return this builder
         */
        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }

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
         * Builds the {@link StockListRequest}.
         *
         * @return a new {@code StockListRequest}
         */
        public StockListRequest build() { return new StockListRequest(this); }
    }
}
