package de.joesst.dev.fulfillmenttools.facilities;

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
    private final String tenantFacilityId;
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

    /** Returns the page size limit. @return the size limit */
    public Integer size() { return size; }

    /** Returns the cursor for pagination. @return the start after ID */
    public String startAfterId() { return startAfterId; }

    /** Returns the facility status filter. @return the status list */
    public List<String> status() { return status; }

    /** Returns the tenant facility ID filter. @return the tenant facility ID */
    public String tenantFacilityId() { return tenantFacilityId; }

    /** Returns the facility type filter. @return the type list */
    public List<String> type() { return type; }

    /** Returns the order by field. @return the order by field */
    public String orderBy() { return orderBy; }

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

    public static Builder builder() { return new Builder(); }

    /** Builder for FacilityListRequest. */
    public static final class Builder {

        /** The page size limit. */
        private Integer size;

        /** The cursor for pagination. */
        private String startAfterId;

        /** The facility status filter. */
        private List<String> status;

        /** The tenant facility ID filter. */
        private String tenantFacilityId;

        /** The facility type filter. */
        private List<String> type;

        /** The order by field. */
        private String orderBy;

        /** Creates a new Builder. */
        public Builder() {}

        /** Sets the page size limit. @param size the page size. @return this builder */
        public Builder size(Integer size) { this.size = size; return this; }

        /** Sets the cursor for pagination. @param startAfterId the start after ID. @return this builder */
        public Builder startAfterId(String startAfterId) { this.startAfterId = startAfterId; return this; }

        /** Sets the facility status filter. @param status the status list. @return this builder */
        public Builder status(List<String> status) { this.status = status; return this; }

        /** Sets the tenant facility ID filter. @param tenantFacilityId the tenant facility ID. @return this builder */
        public Builder tenantFacilityId(String tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }

        /** Sets the facility type filter. @param type the type list. @return this builder */
        public Builder type(List<String> type) { this.type = type; return this; }

        /** Sets the order by field. @param orderBy the order by field. @return this builder */
        public Builder orderBy(String orderBy) { this.orderBy = orderBy; return this; }

        /** Builds and returns a new FacilityListRequest. @return the built request */
        public FacilityListRequest build() { return new FacilityListRequest(this); }
    }
}
