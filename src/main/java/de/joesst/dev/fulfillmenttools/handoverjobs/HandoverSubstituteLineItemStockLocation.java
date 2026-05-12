package de.joesst.dev.fulfillmenttools.handoverjobs;

/**
 * A partial stock location contributing articles for a substitute line item in a handover job.
 *
 * <p>Maps to the {@code SubstituteLineItemPartialStockLocation} schema in the fulfillmenttools
 * OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantPartialStockId Tenant-specific identifier for this partial stock entry.
 * @param stockRef             Reference to the stock entry.
 * @param quantity             Number of units to be picked from this location.
 * @param picked               Number of units that have been picked from this location.
 * @param ratingScore          Optional rating score used for location selection.
 * @param sequenceScore        Optional sequence score for routing optimization.
 * @param locationRef          The id of the physical storage location.
 */
public record HandoverSubstituteLineItemStockLocation(
        String tenantPartialStockId,
        String stockRef,
        Double quantity,
        Double picked,
        Double ratingScore,
        Double sequenceScore,
        String locationRef
) {}
