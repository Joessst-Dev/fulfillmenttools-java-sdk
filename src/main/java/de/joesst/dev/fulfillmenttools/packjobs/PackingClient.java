package de.joesst.dev.fulfillmenttools.packjobs;

import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;
import de.joesst.dev.fulfillmenttools.id.PackJobId;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for accessing and managing pack jobs via the fulfillmenttools API.
 *
 * <p>A pack job represents a packing task to consolidate picked articles into parcels
 * for shipment or collection. This interface provides methods for retrieving, listing,
 * and updating pack jobs.
 */
public interface PackingClient {

    /**
     * Retrieves a single pack job by ID.
     *
     * @param packJobId the unique identifier of the pack job
     * @return the pack job
     * @throws FulfillmenttoolsException if the request fails or the pack job is not found
     */
    PackJob get(PackJobId packJobId);

    /**
     * Asynchronously retrieves a single pack job by ID.
     *
     * @param packJobId the unique identifier of the pack job
     * @return a future that completes with the pack job
     * @throws FulfillmenttoolsException if the request fails or the pack job is not found
     */
    CompletableFuture<PackJob> getAsync(PackJobId packJobId);

    /**
     * Retrieves a page of pack jobs matching the given filter criteria.
     *
     * @param request filter and pagination parameters
     * @return a page of pack jobs
     * @throws FulfillmenttoolsException if the request fails
     */
    Page<PackJob> list(PackJobListRequest request);

    /**
     * Asynchronously retrieves a page of pack jobs matching the given filter criteria.
     *
     * @param request filter and pagination parameters
     * @return a future that completes with a page of pack jobs
     * @throws FulfillmenttoolsException if the request fails
     */
    CompletableFuture<Page<PackJob>> listAsync(PackJobListRequest request);

    /**
     * Retrieves all pack jobs matching the given filter criteria, handling pagination automatically.
     *
     * <p>Returns an iterable that fetches pages on-demand as you iterate.
     * Note: this is a synchronous, blocking operation.
     *
     * @param request filter parameters (pagination settings are ignored)
     * @return an iterable of all matching pack jobs
     * @throws FulfillmenttoolsException if any request fails
     */
    Iterable<PackJob> listAll(PackJobListRequest request);

    /**
     * Updates a pack job with new values.
     *
     * <p>The request must include the current version for optimistic locking.
     *
     * @param packJobId the unique identifier of the pack job
     * @param request the update request containing new values
     * @return the updated pack job
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    PackJob update(PackJobId packJobId, UpdatePackJobRequest request);

    /**
     * Asynchronously updates a pack job with new values.
     *
     * @param packJobId the unique identifier of the pack job
     * @param request the update request containing new values
     * @return a future that completes with the updated pack job
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    CompletableFuture<PackJob> updateAsync(PackJobId packJobId, UpdatePackJobRequest request);
}
