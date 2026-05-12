package de.joesst.dev.fulfillmenttools.storagelocations;

import java.time.Instant;
import java.util.List;
import java.util.Map;

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
        List<Map<String, Object>> traits,
        List<String> scannableCodes,
        Map<String, Object> customAttributes,
        Double schemaVersion,
        Map<String, Object> traitConfig,
        List<Map<String, Object>> runningSequences
) {}
