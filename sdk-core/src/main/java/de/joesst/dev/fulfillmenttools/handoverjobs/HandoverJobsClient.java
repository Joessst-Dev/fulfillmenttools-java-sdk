package de.joesst.dev.fulfillmenttools.handoverjobs;

import de.joesst.dev.fulfillmenttools.id.HandoverJobId;
import de.joesst.dev.fulfillmenttools.model.Page;
import de.joesst.dev.fulfillmenttools.FulfillmenttoolsException;

import java.util.concurrent.CompletableFuture;

/**
 * Client for accessing and managing handover jobs via the fulfillmenttools API.
 *
 * <p>A handover job represents a task of handing over picked articles to a customer or
 * shipping carrier. This interface provides methods for retrieving, listing, updating,
 * and cancelling handover jobs.
 */
public interface HandoverJobsClient {

    /**
     * Retrieves a single handover job by ID.
     *
     * @param handoverJobId the unique identifier of the handover job
     * @return the handover job
     * @throws FulfillmenttoolsException if the request fails or the handover job is not found
     */
    HandoverJob get(HandoverJobId handoverJobId);

    /**
     * Asynchronously retrieves a single handover job by ID.
     *
     * @param handoverJobId the unique identifier of the handover job
     * @return a future that completes with the handover job
     * @throws FulfillmenttoolsException if the request fails or the handover job is not found
     */
    CompletableFuture<HandoverJob> getAsync(HandoverJobId handoverJobId);

    /**
     * Retrieves a page of handover jobs matching the given filter criteria.
     *
     * @param request filter and pagination parameters
     * @return a page of handover jobs
     * @throws FulfillmenttoolsException if the request fails
     */
    Page<HandoverJob> list(HandoverJobListRequest request);

    /**
     * Asynchronously retrieves a page of handover jobs matching the given filter criteria.
     *
     * @param request filter and pagination parameters
     * @return a future that completes with a page of handover jobs
     * @throws FulfillmenttoolsException if the request fails
     */
    CompletableFuture<Page<HandoverJob>> listAsync(HandoverJobListRequest request);

    /**
     * Retrieves all handover jobs matching the given filter criteria, handling pagination automatically.
     *
     * <p>Returns an iterable that fetches pages on-demand as you iterate.
     * Note: this is a synchronous, blocking operation.
     *
     * @param request filter parameters (pagination settings are ignored)
     * @return an iterable of all matching handover jobs
     * @throws FulfillmenttoolsException if any request fails
     */
    Iterable<HandoverJob> listAll(HandoverJobListRequest request);

    /**
     * Updates a handover job with new values.
     *
     * <p>The request must include the current version for optimistic locking.
     *
     * @param handoverJobId the unique identifier of the handover job
     * @param request the update request containing new values
     * @return the updated handover job
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    HandoverJob update(HandoverJobId handoverJobId, UpdateHandoverJobRequest request);

    /**
     * Asynchronously updates a handover job with new values.
     *
     * @param handoverJobId the unique identifier of the handover job
     * @param request the update request containing new values
     * @return a future that completes with the updated handover job
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    CompletableFuture<HandoverJob> updateAsync(HandoverJobId handoverJobId, UpdateHandoverJobRequest request);

    /**
     * Cancels a handover job with a reason.
     *
     * <p>The version parameter is used for optimistic locking to prevent concurrent modification.
     *
     * @param handoverJobId the unique identifier of the handover job
     * @param version the current version of the handover job
     * @param cancelReason the reason for cancellation
     * @return the updated handover job in cancelled state
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    HandoverJob cancel(HandoverJobId handoverJobId, int version, String cancelReason);

    /**
     * Asynchronously cancels a handover job with a reason.
     *
     * @param handoverJobId the unique identifier of the handover job
     * @param version the current version of the handover job
     * @param cancelReason the reason for cancellation
     * @return a future that completes with the updated handover job
     * @throws FulfillmenttoolsException if the request fails or version conflict occurs
     */
    CompletableFuture<HandoverJob> cancelAsync(HandoverJobId handoverJobId, int version, String cancelReason);
}
