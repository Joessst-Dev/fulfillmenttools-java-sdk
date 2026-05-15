package de.joesst.dev.fulfillmenttools.stocks;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.StorageLocationId;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;

import java.util.List;

/**
 * Request parameters for searching stock entries via {@link StocksClient#searchStocks(StockSearchRequest)}.
 *
 * <p>All filter fields are optional; unset filters are omitted from the request body.
 * Multiple values within a single filter field are matched with OR logic; multiple filter
 * fields are combined with AND logic.
 *
 * <p>Example:
 * <pre>{@code
 * StockSearchRequest request = StockSearchRequest.builder()
 *     .tenantArticleId(List.of(new TenantArticleId("art-1"), new TenantArticleId("art-2")))
 *     .facilityRef(List.of(new FacilityId("fac-1")))
 *     .size(50)
 *     .build();
 * }</pre>
 */
public final class StockSearchRequest {

    private final List<TenantArticleId> tenantArticleId;
    private final List<FacilityId> facilityRef;
    private final List<TenantFacilityId> tenantFacilityId;
    private final List<StorageLocationId> locationRef;
    private final Integer size;
    private final String after;

    private StockSearchRequest(Builder builder) {
        this.tenantArticleId = builder.tenantArticleId;
        this.facilityRef = builder.facilityRef;
        this.tenantFacilityId = builder.tenantFacilityId;
        this.locationRef = builder.locationRef;
        this.size = builder.size;
        this.after = builder.after;
    }

    /** Returns the tenant article IDs to filter by, or {@code null} if not set. */
    public List<TenantArticleId> tenantArticleId() { return tenantArticleId; }

    /** Returns the facility references to filter by, or {@code null} if not set. */
    public List<FacilityId> facilityRef() { return facilityRef; }

    /** Returns the tenant facility IDs to filter by, or {@code null} if not set. */
    public List<TenantFacilityId> tenantFacilityId() { return tenantFacilityId; }

    /** Returns the storage location references to filter by, or {@code null} if not set. */
    public List<StorageLocationId> locationRef() { return locationRef; }

    /** Returns the maximum number of results per page, or {@code null} if not set. */
    public Integer size() { return size; }

    /** Returns the cursor for forward pagination, or {@code null} if not set. */
    public String after() { return after; }

    /** Returns a new builder initialized with the current values of this request. */
    public Builder toBuilder() {
        Builder b = new Builder();
        b.tenantArticleId = this.tenantArticleId;
        b.facilityRef = this.facilityRef;
        b.tenantFacilityId = this.tenantFacilityId;
        b.locationRef = this.locationRef;
        b.size = this.size;
        b.after = this.after;
        return b;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private List<TenantArticleId> tenantArticleId;
        private List<FacilityId> facilityRef;
        private List<TenantFacilityId> tenantFacilityId;
        private List<StorageLocationId> locationRef;
        private Integer size;
        private String after;

        private Builder() {}

        /** Filters results to the given tenant article IDs (OR logic within the list). */
        public Builder tenantArticleId(List<TenantArticleId> tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }

        /** Filters results to the given facility references (OR logic within the list). */
        public Builder facilityRef(List<FacilityId> facilityRef) { this.facilityRef = facilityRef; return this; }

        /** Filters results to the given tenant facility IDs (OR logic within the list). */
        public Builder tenantFacilityId(List<TenantFacilityId> tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }

        /** Filters results to the given storage location references (OR logic within the list). */
        public Builder locationRef(List<StorageLocationId> locationRef) { this.locationRef = locationRef; return this; }

        /** Sets the maximum number of results per page (1–250; server default is 20). */
        public Builder size(Integer size) { this.size = size; return this; }

        /** Sets the cursor for forward pagination (value of {@code pageInfo.endCursor} from the previous page). */
        public Builder after(String after) { this.after = after; return this; }

        public StockSearchRequest build() { return new StockSearchRequest(this); }
    }
}
