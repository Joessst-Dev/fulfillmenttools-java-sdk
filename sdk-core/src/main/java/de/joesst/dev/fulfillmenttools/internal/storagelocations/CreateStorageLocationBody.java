package de.joesst.dev.fulfillmenttools.internal.storagelocations;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationSequenceItem;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocationTraitConfigEntry;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

import java.util.List;

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
        CustomAttributes customAttributes
) {}
