package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.model.Coordinates;

import java.time.Instant;
import java.util.Map;

/**
 * Represents a fulfillmenttools facility (warehouse, store, dark store, etc.).
 */
public record Facility(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String tenantFacilityId,
        String name,
        String status,
        String type,
        String companyName,
        FacilityAddress address,
        Coordinates coordinates,
        Map<String, Object> customAttributes
) {}
