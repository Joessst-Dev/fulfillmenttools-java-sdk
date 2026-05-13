package de.joesst.dev.fulfillmenttools.stocks;

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
 * @param facilityRef      The fulfillmenttools-internal facility identifier.
 * @param tenantFacilityId The tenant-specific {@link TenantFacilityId} facility identifier.
 */
public record StockFacilityReferences(
        String facilityRef,
        TenantFacilityId tenantFacilityId
) {}
