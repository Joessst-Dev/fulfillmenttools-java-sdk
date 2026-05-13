package de.joesst.dev.fulfillmenttools.storagelocations;

import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for managing storage locations in the fulfillmenttools storagelocations module.
 *
 * <p>Provides synchronous and asynchronous operations to retrieve, list, create, update,
 * and delete storage locations within a facility.
 */
public interface StorageLocationsClient {

    /**
     * Retrieves a single storage location by ID.
     *
     * @param facilityId        the facility ID
     * @param storageLocationId the storage location ID
     * @return the storage location
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StorageLocation get(String facilityId, String storageLocationId);

    /**
     * Retrieves a single storage location by ID asynchronously.
     *
     * @param facilityId        the facility ID
     * @param storageLocationId the storage location ID
     * @return a {@code CompletableFuture} that resolves to the storage location
     */
    CompletableFuture<StorageLocation> getAsync(String facilityId, String storageLocationId);

    /**
     * Lists storage locations for a facility, returning one page of results.
     *
     * @param facilityId the facility ID
     * @param request    the list request with filters and pagination
     * @return a page of storage locations
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<StorageLocation> list(String facilityId, StorageLocationListRequest request);

    /**
     * Lists storage locations for a facility asynchronously, returning one page of results.
     *
     * @param facilityId the facility ID
     * @param request    the list request with filters and pagination
     * @return a {@code CompletableFuture} that resolves to a page of storage locations
     */
    CompletableFuture<Page<StorageLocation>> listAsync(String facilityId, StorageLocationListRequest request);

    /**
     * Lists all storage locations for a facility by automatically iterating through pages.
     *
     * @param facilityId the facility ID
     * @param request    the list request with filters
     * @return an {@code Iterable} over all matching storage locations
     */
    Iterable<StorageLocation> listAll(String facilityId, StorageLocationListRequest request);

    /**
     * Creates a new storage location in a facility.
     *
     * @param facilityId the facility ID
     * @param request    the creation request
     * @return the created storage location
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StorageLocation create(String facilityId, CreateStorageLocationRequest request);

    /**
     * Creates a new storage location in a facility asynchronously.
     *
     * @param facilityId the facility ID
     * @param request    the creation request
     * @return a {@code CompletableFuture} that resolves to the created storage location
     */
    CompletableFuture<StorageLocation> createAsync(String facilityId, CreateStorageLocationRequest request);

    /**
     * Updates an existing storage location.
     *
     * @param facilityId        the facility ID
     * @param storageLocationId the storage location ID to update
     * @param request           the update request
     * @return the updated storage location
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StorageLocation update(String facilityId, String storageLocationId, UpdateStorageLocationRequest request);

    /**
     * Updates an existing storage location asynchronously.
     *
     * @param facilityId        the facility ID
     * @param storageLocationId the storage location ID to update
     * @param request           the update request
     * @return a {@code CompletableFuture} that resolves to the updated storage location
     */
    CompletableFuture<StorageLocation> updateAsync(String facilityId, String storageLocationId, UpdateStorageLocationRequest request);

    /**
     * Deletes a storage location.
     *
     * @param facilityId        the facility ID
     * @param storageLocationId the storage location ID to delete
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    void delete(String facilityId, String storageLocationId);

    /**
     * Deletes a storage location asynchronously.
     *
     * @param facilityId        the facility ID
     * @param storageLocationId the storage location ID to delete
     * @return a {@code CompletableFuture} that completes when the deletion is done
     */
    CompletableFuture<Void> deleteAsync(String facilityId, String storageLocationId);
}
