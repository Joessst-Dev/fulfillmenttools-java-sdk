package de.joesst.dev.fulfillmenttools.internal.storagelocations;

import java.util.List;
import java.util.Map;

record UpdateStorageLocationBody(Integer version, List<ModifyStorageLocationAction> actions) {

    record ModifyStorageLocationAction(
            String action,
            String name,
            String type,
            List<String> scannableCodes,
            List<Map<String, Object>> runningSequences,
            String information,
            String tenantLocationId,
            String zoneRef,
            Map<String, Object> traitConfig,
            Map<String, Object> customAttributes
    ) {
        ModifyStorageLocationAction(
                String name, String type, List<String> scannableCodes,
                List<Map<String, Object>> runningSequences, String information,
                String tenantLocationId, String zoneRef,
                Map<String, Object> traitConfig, Map<String, Object> customAttributes
        ) {
            this("ModifyStorageLocation", name, type, scannableCodes, runningSequences,
                    information, tenantLocationId, zoneRef, traitConfig, customAttributes);
        }
    }
}
