package de.joesst.dev.fulfillmenttools.sourcingoptions;

import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;

import java.util.List;

/**
 * A facility node participating in a sourcing option, representing one source of stock
 * in the fulfillment plan.
 *
 * <p>Maps to the {@code SourcingOptionNode} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id               Unique identifier of this node.
 * @param facilityRef      Reference to the fulfillmenttools facility.
 * @param tenantFacilityId Tenant-specific {@link TenantFacilityId} facility identifier.
 * @param type             Node type (e.g. {@code SHIP_FROM_STORE}, {@code WAREHOUSE}).
 * @param isPickUpLocation Whether this node is a click-and-collect pickup location.
 * @param lineItems        The line items to be fulfilled from this node.
 */
public record SourcingOptionNode(
        String id,
        String facilityRef,
        TenantFacilityId tenantFacilityId,
        String type,
        Boolean isPickUpLocation,
        List<SourcingOptionNodeLineItem> lineItems
) {}
