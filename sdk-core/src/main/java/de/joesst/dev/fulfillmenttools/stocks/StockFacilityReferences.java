package de.joesst.dev.fulfillmenttools.stocks;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;

/**
 * A compact reference to the facility associated with a stock entry.
 *
 * <p>Maps to the {@code StockFacilityReferences} schema in the fulfillmenttools OpenAPI spec.
 * This object is embedded in {@link StockItem} to provide facility-identification data
 * alongside the stock record without requiring a separate facility lookup.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param facilityRef      The fulfillmenttools-internal {@link FacilityId} facility identifier.
 * @param tenantFacilityId The tenant-specific {@link TenantFacilityId} facility identifier.
 */
public record StockFacilityReferences(
        FacilityId facilityRef,
        TenantFacilityId tenantFacilityId
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private FacilityId facilityRef;
        private TenantFacilityId tenantFacilityId;

        public Builder facilityRef(FacilityId facilityRef) {
            this.facilityRef = facilityRef;
            return this;
        }

        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) {
            this.tenantFacilityId = tenantFacilityId;
            return this;
        }

        public StockFacilityReferences build() {
            return new StockFacilityReferences(facilityRef, tenantFacilityId);
        }
    }
}
