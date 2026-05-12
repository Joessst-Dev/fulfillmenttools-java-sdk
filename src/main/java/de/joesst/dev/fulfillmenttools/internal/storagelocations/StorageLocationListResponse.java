package de.joesst.dev.fulfillmenttools.internal.storagelocations;

import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocation;

import java.util.List;

record StorageLocationListResponse(List<StorageLocation> storageLocations, String nextCursor) {}
