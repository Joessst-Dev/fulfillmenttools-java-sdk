package de.joesst.dev.fulfillmenttools.facilitygroups;

import de.joesst.dev.fulfillmenttools.id.FacilityGroupId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Client for managing facility groups in the fulfillmenttools platform.
 * Provides synchronous and asynchronous operations for CRUD and search operations.
 */
public interface FacilityGroupsClient {

    /**
     * Retrieves a facility group by its ID.
     *
     * @param facilityGroupId the facility group ID
     * @return the facility group
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    FacilityGroup get(FacilityGroupId facilityGroupId);

    /**
     * Asynchronously retrieves a facility group by its ID.
     *
     * @param facilityGroupId the facility group ID
     * @return a CompletableFuture containing the facility group
     */
    CompletableFuture<FacilityGroup> getAsync(FacilityGroupId facilityGroupId);

    /**
     * Lists facility groups with pagination.
     *
     * @param request the list request with pagination parameters
     * @return a page of facility groups
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<FacilityGroup> list(FacilityGroupListRequest request);

    /**
     * Lists all facility groups by automatically handling pagination.
     *
     * @param request the list request
     * @return an iterable over all facility groups
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Iterable<FacilityGroup> listAll(FacilityGroupListRequest request);

    /**
     * Asynchronously lists facility groups with pagination.
     *
     * @param request the list request with pagination parameters
     * @return a CompletableFuture containing a page of facility groups
     */
    CompletableFuture<Page<FacilityGroup>> listAsync(FacilityGroupListRequest request);

    /**
     * Creates a new facility group.
     *
     * @param request the create request containing facility group data
     * @return the created facility group
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    FacilityGroup create(CreateFacilityGroupRequest request);

    /**
     * Asynchronously creates a new facility group.
     *
     * @param request the create request containing facility group data
     * @return a CompletableFuture containing the created facility group
     */
    CompletableFuture<FacilityGroup> createAsync(CreateFacilityGroupRequest request);

    /**
     * Updates an existing facility group.
     *
     * @param facilityGroupId the facility group ID to update
     * @param request the update request containing the new values
     * @return the updated facility group
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    FacilityGroup update(FacilityGroupId facilityGroupId, UpdateFacilityGroupRequest request);

    /**
     * Asynchronously updates an existing facility group.
     *
     * @param facilityGroupId the facility group ID to update
     * @param request the update request containing the new values
     * @return a CompletableFuture containing the updated facility group
     */
    CompletableFuture<FacilityGroup> updateAsync(FacilityGroupId facilityGroupId, UpdateFacilityGroupRequest request);

    /**
     * Deletes a facility group.
     *
     * @param facilityGroupId the facility group ID to delete
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    void delete(FacilityGroupId facilityGroupId);

    /**
     * Asynchronously deletes a facility group.
     *
     * @param facilityGroupId the facility group ID to delete
     * @return a CompletableFuture that completes when the deletion is done
     */
    CompletableFuture<Void> deleteAsync(FacilityGroupId facilityGroupId);

    /**
     * Adds facilities to a facility group.
     *
     * @param facilityGroupId the facility group ID
     * @param facilityIds the facility IDs to add
     * @param version the current version for optimistic locking
     * @return the updated facility group
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    FacilityGroup addFacilities(FacilityGroupId facilityGroupId, List<FacilityId> facilityIds, Integer version);

    /**
     * Asynchronously adds facilities to a facility group.
     *
     * @param facilityGroupId the facility group ID
     * @param facilityIds the facility IDs to add
     * @param version the current version for optimistic locking
     * @return a CompletableFuture containing the updated facility group
     */
    CompletableFuture<FacilityGroup> addFacilitiesAsync(FacilityGroupId facilityGroupId, List<FacilityId> facilityIds, Integer version);

    /**
     * Removes facilities from a facility group.
     *
     * @param facilityGroupId the facility group ID
     * @param facilityIds the facility IDs to remove
     * @param version the current version for optimistic locking
     * @return the updated facility group
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    FacilityGroup removeFacilities(FacilityGroupId facilityGroupId, List<FacilityId> facilityIds, Integer version);

    /**
     * Asynchronously removes facilities from a facility group.
     *
     * @param facilityGroupId the facility group ID
     * @param facilityIds the facility IDs to remove
     * @param version the current version for optimistic locking
     * @return a CompletableFuture containing the updated facility group
     */
    CompletableFuture<FacilityGroup> removeFacilitiesAsync(FacilityGroupId facilityGroupId, List<FacilityId> facilityIds, Integer version);

    /**
     * Searches for facility groups matching the given query.
     *
     * @param request the search request with query and pagination parameters
     * @return a page of matching facility groups
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<FacilityGroup> search(FacilityGroupSearchRequest request);

    /**
     * Searches for all facility groups matching the given query by automatically handling pagination.
     *
     * @param request the search request with query
     * @return an iterable over all matching facility groups
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Iterable<FacilityGroup> searchAll(FacilityGroupSearchRequest request);

    /**
     * Asynchronously searches for facility groups matching the given query.
     *
     * @param request the search request with query and pagination parameters
     * @return a CompletableFuture containing a page of matching facility groups
     */
    CompletableFuture<Page<FacilityGroup>> searchAsync(FacilityGroupSearchRequest request);
}
