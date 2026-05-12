package de.joesst.dev.fulfillmenttools.storagelocations;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

public interface StorageLocationsClient {

    StorageLocation get(String facilityId, String storageLocationId);
    CompletableFuture<StorageLocation> getAsync(String facilityId, String storageLocationId);

    Page<StorageLocation> list(String facilityId, StorageLocationListRequest request);
    CompletableFuture<Page<StorageLocation>> listAsync(String facilityId, StorageLocationListRequest request);

    Iterable<StorageLocation> listAll(String facilityId, StorageLocationListRequest request);

    StorageLocation create(String facilityId, CreateStorageLocationRequest request);
    CompletableFuture<StorageLocation> createAsync(String facilityId, CreateStorageLocationRequest request);

    StorageLocation update(String facilityId, String storageLocationId, UpdateStorageLocationRequest request);
    CompletableFuture<StorageLocation> updateAsync(String facilityId, String storageLocationId, UpdateStorageLocationRequest request);

    void delete(String facilityId, String storageLocationId);
    CompletableFuture<Void> deleteAsync(String facilityId, String storageLocationId);
}
