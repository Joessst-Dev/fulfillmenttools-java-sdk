package de.joesst.dev.fulfillmenttools.returns;

import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a return in the fulfillmenttools system.
 *
 * @param id the platform-generated return identifier
 * @param version the optimistic-locking version
 * @param created the timestamp when this return was created
 * @param lastModified the timestamp of the last modification
 * @param status the current return status
 * @param shortId a short human-readable identifier for this return
 * @param processRef the reference to the process associated with this return
 * @param tenantOrderId the tenant's external order reference number
 * @param originFacilityRefs references to the origin facilities for this return
 * @param scannableCodes scannable codes (e.g., barcodes) for this return
 * @param consumerAddresses addresses associated with this return
 * @param returnableLineItems line items that can be returned
 * @param notReturnableLineItems line items that cannot be returned
 * @param itemReturns the individual item returns for this return
 * @param anonymized whether this return has been anonymized for GDPR compliance
 * @param customAttributes free-form custom attributes
 */
public record Return(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String status,
        String shortId,
        String processRef,
        String tenantOrderId,
        List<String> originFacilityRefs,
        List<String> scannableCodes,
        List<ConsumerAddress> consumerAddresses,
        List<ReturnJobLineItem> returnableLineItems,
        List<ReturnJobLineItem> notReturnableLineItems,
        List<ReturnItem> itemReturns,
        Boolean anonymized,
        Map<String, Object> customAttributes
) {}
