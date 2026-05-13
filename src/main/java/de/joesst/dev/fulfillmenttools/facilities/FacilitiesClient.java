package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for managing facilities in the fulfillmenttools platform.
 *
 * <p>Provides synchronous and asynchronous methods for CRUD operations and listing facilities,
 * as well as a powerful search API for querying facilities by multiple criteria.
 */
public interface FacilitiesClient {

    /**
     * Retrieves a single facility by ID.
     *
     * @param facilityId unique identifier of the facility
     * @return the facility
     * @throws FulfillmenttoolsException if the facility is not found or a request error occurs
     */
    Facility get(String facilityId);

    /**
     * Asynchronously retrieves a single facility by ID.
     *
     * @param facilityId unique identifier of the facility
     * @return a future that completes with the facility
     */
    CompletableFuture<Facility> getAsync(String facilityId);

    /**
     * Lists facilities with optional filtering and pagination.
     *
     * @param request the list request parameters
     * @return a single page of facilities
     * @throws FulfillmenttoolsException if a request error occurs
     */
    Page<Facility> list(FacilityListRequest request);

    /**
     * Asynchronously lists facilities with optional filtering and pagination.
     *
     * @param request the list request parameters
     * @return a future that completes with a single page of facilities
     */
    CompletableFuture<Page<Facility>> listAsync(FacilityListRequest request);

    /**
     * Iterates through all facilities, automatically handling pagination.
     *
     * @param request the list request parameters
     * @return an iterable over all matching facilities
     */
    Iterable<Facility> listAll(FacilityListRequest request);

    /**
     * Searches facilities using a complex query with multiple filter criteria.
     *
     * @param request the search request containing the query and pagination parameters
     * @return a single page of search results
     * @throws FulfillmenttoolsException if a request error occurs
     */
    Page<Facility> search(FacilitySearchRequest request);

    /**
     * Asynchronously searches facilities using a complex query.
     *
     * @param request the search request containing the query and pagination parameters
     * @return a future that completes with a single page of search results
     */
    CompletableFuture<Page<Facility>> searchAsync(FacilitySearchRequest request);

    /**
     * Iterates through all search results, automatically handling pagination.
     *
     * @param request the search request containing the query and pagination parameters
     * @return an iterable over all matching facilities
     */
    Iterable<Facility> searchAll(FacilitySearchRequest request);

    /**
     * Creates a new facility.
     *
     * @param request the facility creation payload
     * @return the created facility with assigned ID and metadata
     * @throws FulfillmenttoolsException if validation fails or a request error occurs
     */
    Facility create(CreateFacilityRequest request);

    /**
     * Asynchronously creates a new facility.
     *
     * @param request the facility creation payload
     * @return a future that completes with the created facility
     */
    CompletableFuture<Facility> createAsync(CreateFacilityRequest request);

    /**
     * Updates an existing facility with partial changes.
     *
     * @param facilityId unique identifier of the facility to update
     * @param request the update payload with new field values
     * @return the updated facility
     * @throws FulfillmenttoolsException if the facility is not found or a request error occurs
     */
    Facility update(String facilityId, UpdateFacilityRequest request);

    /**
     * Asynchronously updates an existing facility.
     *
     * @param facilityId unique identifier of the facility to update
     * @param request the update payload with new field values
     * @return a future that completes with the updated facility
     */
    CompletableFuture<Facility> updateAsync(String facilityId, UpdateFacilityRequest request);

    /**
     * Replaces an entire facility, overwriting all fields.
     *
     * @param facilityId unique identifier of the facility to replace
     * @param request the replacement facility payload
     * @return the replaced facility
     * @throws FulfillmenttoolsException if the facility is not found or a request error occurs
     */
    Facility replace(String facilityId, CreateFacilityRequest request);

    /**
     * Asynchronously replaces an entire facility.
     *
     * @param facilityId unique identifier of the facility to replace
     * @param request the replacement facility payload
     * @return a future that completes with the replaced facility
     */
    CompletableFuture<Facility> replaceAsync(String facilityId, CreateFacilityRequest request);

    /**
     * Deletes a facility.
     *
     * @param facilityId unique identifier of the facility to delete
     * @throws FulfillmenttoolsException if the facility is not found or a request error occurs
     */
    void delete(String facilityId);

    /**
     * Deletes a facility, optionally forcing deletion even if it has associations.
     *
     * @param facilityId unique identifier of the facility to delete
     * @param forceDeletion if {@code true}, bypasses soft-delete constraints
     * @throws FulfillmenttoolsException if a request error occurs
     */
    void delete(String facilityId, boolean forceDeletion);

    /**
     * Asynchronously deletes a facility.
     *
     * @param facilityId unique identifier of the facility to delete
     * @return a future that completes when the deletion is done
     */
    CompletableFuture<Void> deleteAsync(String facilityId);

    /**
     * Asynchronously deletes a facility, optionally forcing deletion.
     *
     * @param facilityId unique identifier of the facility to delete
     * @param forceDeletion if {@code true}, bypasses soft-delete constraints
     * @return a future that completes when the deletion is done
     */
    CompletableFuture<Void> deleteAsync(String facilityId, boolean forceDeletion);
}
