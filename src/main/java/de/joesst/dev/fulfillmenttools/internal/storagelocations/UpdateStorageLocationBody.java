package de.joesst.dev.fulfillmenttools.internal.storagelocations;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationSequenceItem;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationTraitConfigEntry;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
record UpdateStorageLocationBody(Integer version, List<ModifyStorageLocationAction> actions) {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    record ModifyStorageLocationAction(
            String action,
            String name,
            String type,
            List<String> scannableCodes,
            List<StorageLocationSequenceItem> runningSequences,
            String information,
            String tenantLocationId,
            String zoneRef,
            List<StorageLocationTraitConfigEntry> traitConfig,
            Map<String, Object> customAttributes
    ) {
        ModifyStorageLocationAction(
                String name, String type, List<String> scannableCodes,
                List<StorageLocationSequenceItem> runningSequences, String information,
                String tenantLocationId, String zoneRef,
                List<StorageLocationTraitConfigEntry> traitConfig, Map<String, Object> customAttributes
        ) {
            this("ModifyStorageLocation", name, type, scannableCodes, runningSequences,
                    information, tenantLocationId, zoneRef, traitConfig, customAttributes);
        }
    }
}
