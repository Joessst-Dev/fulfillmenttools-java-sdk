package de.joesst.dev.fulfillmenttools.internal.storagelocations;

import java.util.List;
import java.util.Map;

record CreateStorageLocationBody(
        String name,
        String type,
        List<String> scannableCodes,
        List<Map<String, Object>> runningSequences,
        String information,
        String tenantLocationId,
        String zoneRef,
        String zoneName,
        Map<String, Object> traitConfig,
        List<Map<String, Object>> traits,
        Map<String, Object> customAttributes
) {}
