package de.joesst.dev.fulfillmenttools.storagelocations;

public record StorageLocation(
        String id,
        String name,
        String type,
        String facilityRef
) {}
