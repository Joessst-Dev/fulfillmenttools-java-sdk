package de.joesst.dev.fulfillmenttools.facilityconnections;

import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import de.joesst.dev.fulfillmenttools.id.ConnectionId;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for managing facility connections in the fulfillmenttools platform.
 *
 * <p>A facility connection links a source facility to a carrier and target (customer, managed facility, or supplier),
 * defining routing rules including cutoff times, packaging units, delivery costs, and transit times.
 * Provides synchronous and asynchronous methods for CRUD operations and listing.
 */
public interface FacilityConnectionsClient {

    /**
     * Retrieves a single facility connection by ID.
     *
     * @param facilityId unique identifier of the source facility
     * @param connectionId unique identifier of the connection
     * @return the facility connection
     * @throws FulfillmenttoolsException if the connection is not found or a request error occurs
     */
    FacilityConnection get(FacilityId facilityId, ConnectionId connectionId);

    /**
     * Asynchronously retrieves a single facility connection by ID.
     *
     * @param facilityId unique identifier of the source facility
     * @param connectionId unique identifier of the connection
     * @return a future that completes with the facility connection
     */
    CompletableFuture<FacilityConnection> getAsync(FacilityId facilityId, ConnectionId connectionId);

    /**
     * Lists facility connections with optional filtering and pagination.
     *
     * @param facilityId unique identifier of the source facility
     * @param request the list request parameters
     * @return a single page of connections
     * @throws FulfillmenttoolsException if a request error occurs
     */
    Page<FacilityConnection> list(FacilityId facilityId, FacilityConnectionListRequest request);

    /**
     * Iterates through all facility connections, automatically handling pagination.
     *
     * @param facilityId unique identifier of the source facility
     * @param request the list request parameters
     * @return an iterable over all matching connections
     */
    Iterable<FacilityConnection> listAll(FacilityId facilityId, FacilityConnectionListRequest request);

    /**
     * Asynchronously lists facility connections with optional filtering and pagination.
     *
     * @param facilityId unique identifier of the source facility
     * @param request the list request parameters
     * @return a future that completes with a single page of connections
     */
    CompletableFuture<Page<FacilityConnection>> listAsync(FacilityId facilityId, FacilityConnectionListRequest request);

    /**
     * Creates a new facility connection.
     *
     * @param facilityId unique identifier of the source facility
     * @param request the connection creation payload
     * @return the created connection with assigned ID and metadata
     * @throws FulfillmenttoolsException if validation fails or a request error occurs
     */
    FacilityConnection create(FacilityId facilityId, CreateFacilityConnectionRequest request);

    /**
     * Asynchronously creates a new facility connection.
     *
     * @param facilityId unique identifier of the source facility
     * @param request the connection creation payload
     * @return a future that completes with the created connection
     */
    CompletableFuture<FacilityConnection> createAsync(FacilityId facilityId, CreateFacilityConnectionRequest request);

    /**
     * Updates an existing facility connection with partial changes.
     *
     * @param facilityId unique identifier of the source facility
     * @param connectionId unique identifier of the connection to update
     * @param request the update payload with new field values
     * @return the updated connection
     * @throws FulfillmenttoolsException if the connection is not found or a request error occurs
     */
    FacilityConnection update(FacilityId facilityId, ConnectionId connectionId, UpdateFacilityConnectionRequest request);

    /**
     * Asynchronously updates an existing facility connection.
     *
     * @param facilityId unique identifier of the source facility
     * @param connectionId unique identifier of the connection to update
     * @param request the update payload with new field values
     * @return a future that completes with the updated connection
     */
    CompletableFuture<FacilityConnection> updateAsync(FacilityId facilityId, ConnectionId connectionId, UpdateFacilityConnectionRequest request);

    /**
     * Deletes a facility connection.
     *
     * @param facilityId unique identifier of the source facility
     * @param connectionId unique identifier of the connection to delete
     * @throws FulfillmenttoolsException if the connection is not found or a request error occurs
     */
    void delete(FacilityId facilityId, ConnectionId connectionId);

    /**
     * Asynchronously deletes a facility connection.
     *
     * @param facilityId unique identifier of the source facility
     * @param connectionId unique identifier of the connection to delete
     * @return a future that completes when the deletion is done
     */
    CompletableFuture<Void> deleteAsync(FacilityId facilityId, ConnectionId connectionId);
}
