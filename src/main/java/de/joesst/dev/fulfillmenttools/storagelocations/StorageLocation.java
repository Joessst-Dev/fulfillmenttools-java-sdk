package de.joesst.dev.fulfillmenttools.storagelocations;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a storage location within a facility.
 */
public record StorageLocation(
        String id,
        Integer version,
        Instant created,
        Instant lastModified,
        String facilityRef,
        String name,
        String type,
        String tenantLocationId,
        String zoneName,
        String zoneRef,
        String information,
        List<String> traits,
        List<String> scannableCodes,
        Map<String, Object> customAttributes
) {}
