package de.joesst.dev.fulfillmenttools.sourcingoptions;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;

import java.util.Map;

/**
 * Listing details for an article within a sourcing option, including stock availability
 * and listing configuration at a specific facility.
 *
 * <p>Maps to the {@code SourcingOptionListingDetails} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param listingRef        Reference to the listing.
 * @param facilityRef       Reference to the facility this listing belongs to.
 * @param tenantFacilityId  Tenant-specific {@link TenantFacilityId} facility identifier.
 * @param tenantArticleId   Tenant-specific article identifier.
 * @param stockInformation  Stock availability details for this listing.
 * @param customAttributes  Free-form custom attributes.
 */
public record SourcingOptionListingDetails(
        String listingRef,
        FacilityId facilityRef,
        TenantFacilityId tenantFacilityId,
        String tenantArticleId,
        SourcingOptionStockInformation stockInformation,
        Map<String, Object> customAttributes
) {}
