package de.joesst.dev.fulfillmenttools.internal.storagelocations;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationSequenceItem;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationTraitConfigEntry;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
record CreateStorageLocationBody(
        String name,
        String type,
        List<String> scannableCodes,
        List<StorageLocationSequenceItem> runningSequences,
        String information,
        String tenantLocationId,
        String zoneRef,
        String zoneName,
        List<StorageLocationTraitConfigEntry> traitConfig,
        List<String> traits,
        Map<String, Object> customAttributes
) {}
