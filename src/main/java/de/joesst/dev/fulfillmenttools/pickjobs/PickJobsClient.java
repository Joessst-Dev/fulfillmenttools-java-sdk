package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

import java.util.concurrent.CompletableFuture;

/**
 * Client for accessing and managing pick jobs via the fulfillmenttools API.
 *
 * <p>A pick job represents a fulfilment task to pick articles for an order at a facility.
 * This interface provides methods for retrieving, listing, updating, and managing the
 * workflow state of pick jobs.
 */
public interface PickJobsClient {

    /**
     * Retrieves a single pick job by ID.
     *
     * @param pickJobId the unique identifier of the pick job
     * @return the pick job
     * @throws FulfillmenttoolsException if the request fails or the pick job is not found
     */
    PickJob get(String pickJobId);

    /**
     * Asynchronously retrieves a single pick job by ID.
     *
     * @param pickJobId the unique identifier of the pick job
     * @return a future that completes with the pick job
     * @throws FulfillmenttoolsException if the request fails or the pick job is not found
     */
    CompletableFuture<PickJob> getAsync(String pickJobId);

    /**
     * Retrieves a page of pick jobs matching the given filter criteria.
     *
     * @param request filter and pagination parameters
     * @return a page of pick jobs
     * @throws FulfillmenttoolsException if the request fails
     */
    Page<PickJob> list(PickJobListRequest request);

    /**
     * Asynchronously retrieves a page of pick jobs matching the given filter criteria.
     *
     * @param request filter and pagination parameters
     * @return a future that completes with a page of pick jobs
     * @throws FulfillmenttoolsException if the request fails
     */
    CompletableFuture<Page<PickJob>> listAsync(PickJobListRequest request);

    /**
     * Retrieves all pick jobs matching the given filter criteria, handling pagination automatically.
     *
     * <p>Returns an iterable that fetches pages on-demand as you iterate.
     * Note: this is a synchronous, blocking operation.
     *
     * @param request filter parameters (pagination settings are ignored)
     * @return an iterable of all matching pick jobs
     * @throws FulfillmenttoolsException if any request fails
     */
    Iterable<PickJob> listAll(PickJobListRequest request);

    /**
     * Updates a pick job with new values.
     *
     * <p>The request must include the current version for optimistic locking.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param request the update request containing new values
     * @return the updated pick job
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    PickJob update(String pickJobId, UpdatePickJobRequest request);

    /**
     * Asynchronously updates a pick job with new values.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param request the update request containing new values
     * @return a future that completes with the updated pick job
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    CompletableFuture<PickJob> updateAsync(String pickJobId, UpdatePickJobRequest request);

    /**
     * Transitions a pick job to the aborted state.
     *
     * <p>The version parameter is used for optimistic locking to prevent concurrent modification.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param version the current version of the pick job
     * @return the updated pick job in aborted state
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    PickJob abort(String pickJobId, int version);

    /**
     * Asynchronously transitions a pick job to the aborted state.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param version the current version of the pick job
     * @return a future that completes with the updated pick job
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    CompletableFuture<PickJob> abortAsync(String pickJobId, int version);

    /**
     * Transitions a pick job to the restarted state.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param version the current version of the pick job
     * @return the updated pick job in restarted state
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    PickJob restart(String pickJobId, int version);

    /**
     * Asynchronously transitions a pick job to the restarted state.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param version the current version of the pick job
     * @return a future that completes with the updated pick job
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    CompletableFuture<PickJob> restartAsync(String pickJobId, int version);

    /**
     * Transitions a pick job to the reset state.
     *
     * <p>Reset allows the pick job to be restarted from the beginning.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param version the current version of the pick job
     * @return the updated pick job in reset state
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    PickJob reset(String pickJobId, int version);

    /**
     * Asynchronously transitions a pick job to the reset state.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param version the current version of the pick job
     * @return a future that completes with the updated pick job
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    CompletableFuture<PickJob> resetAsync(String pickJobId, int version);

    /**
     * Transitions a pick job to the obsolete state.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param version the current version of the pick job
     * @return the updated pick job in obsolete state
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    PickJob obsolete(String pickJobId, int version);

    /**
     * Asynchronously transitions a pick job to the obsolete state.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param version the current version of the pick job
     * @return a future that completes with the updated pick job
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    CompletableFuture<PickJob> obsoleteAsync(String pickJobId, int version);

    /**
     * Transitions a pick job to the started state.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param version the current version of the pick job
     * @return the updated pick job in started state
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    PickJob start(String pickJobId, int version);

    /**
     * Asynchronously transitions a pick job to the started state.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param version the current version of the pick job
     * @return a future that completes with the updated pick job
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    CompletableFuture<PickJob> startAsync(String pickJobId, int version);

    /**
     * Transitions a pick job to the paused state.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param version the current version of the pick job
     * @return the updated pick job in paused state
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    PickJob pause(String pickJobId, int version);

    /**
     * Asynchronously transitions a pick job to the paused state.
     *
     * @param pickJobId the unique identifier of the pick job
     * @param version the current version of the pick job
     * @return a future that completes with the updated pick job
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    CompletableFuture<PickJob> pauseAsync(String pickJobId, int version);
}
