package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a single item return in the fulfillmenttools system.
 *
 * @param id the platform-generated item return identifier
 * @param created the timestamp when this item return was created
 * @param lastModified the timestamp of the last modification
 * @param status the current item return status
 * @param returnFacilityRef the reference to the facility where this item should be returned
 * @param tenantOrderId the tenant's external {@link TenantOrderId} order reference number
 * @param scannableCodes scannable codes (e.g., barcodes) for this item return
 * @param parcelRefs references to the parcels involved in this item return
 * @param returnedLineItems the line items being returned
 * @param customAttributes free-form custom attributes
 */
public record ReturnItem(
        String id,
        Instant created,
        Instant lastModified,
        String status,
        FacilityId returnFacilityRef,
        TenantOrderId tenantOrderId,
        List<String> scannableCodes,
        List<String> parcelRefs,
        List<ReturnItemLineItem> returnedLineItems,
        Map<String, Object> customAttributes
) {}
