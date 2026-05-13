package de.joesst.dev.fulfillmenttools.pickjobs;

import java.util.Map;

/**
 * Describes a partial stock location from which articles can be picked for a pick line item.
 *
 * <p>Maps to the {@code PickJobLineItemPartialStockLocation} schema in the fulfillmenttools
 * OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param tenantPartialStockId Tenant-specific identifier for this partial stock entry.
 * @param stockRef             Reference to the stock entry.
 * @param available            Number of units available at this location.
 * @param quantity             Number of units that should be picked from this location.
 * @param picked               Number of units that have been picked from this location.
 * @param ratingScore          Optional rating score used for location selection.
 * @param sequenceScore        Optional sequence score for routing optimization.
 * @param stockEmptied         Whether the stock at this location was fully depleted.
 * @param location             The physical location details.
 * @param stockProperties      Optional additional stock properties (free-form string map).
 * @param zoneName             Name of the zone this location belongs to.
 * @param zoneRef              Reference to the zone this location belongs to.
 */
public record PickJobLineItemPartialStockLocation(
        String tenantPartialStockId,
        String stockRef,
        Integer available,
        Integer quantity,
        Integer picked,
        Double ratingScore,
        Double sequenceScore,
        Boolean stockEmptied,
        PickJobStockLocation location,
        Map<String, String> stockProperties,
        String zoneName,
        String zoneRef
) {}
