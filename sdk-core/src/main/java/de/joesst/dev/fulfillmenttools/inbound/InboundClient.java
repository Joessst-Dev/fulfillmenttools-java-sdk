package de.joesst.dev.fulfillmenttools.inbound;

import de.joesst.dev.fulfillmenttools.id.StowJobId;
import de.joesst.dev.fulfillmenttools.model.Page;

import java.util.concurrent.CompletableFuture;

/**
 * Client for managing stow jobs in the fulfillmenttools inbound module.
 *
 * <p>Provides synchronous and asynchronous operations to retrieve, list, create, update,
 * and manage the lifecycle of stow jobs (e.g. start, pause, cancel, reopen, close).
 */
public interface InboundClient {

    /**
     * Retrieves a single stow job by ID.
     *
     * @param stowJobId the ID of the stow job
     * @return the stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob get(StowJobId stowJobId);

    /**
     * Retrieves a single stow job by ID asynchronously.
     *
     * @param stowJobId the ID of the stow job
     * @return a {@code CompletableFuture} that resolves to the stow job
     */
    CompletableFuture<StowJob> getAsync(StowJobId stowJobId);

    /**
     * Lists stow jobs matching the specified criteria, returning one page of results.
     *
     * @param request the list request with filters and pagination
     * @return a page of stow jobs
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    Page<StowJob> list(StowJobListRequest request);

    /**
     * Lists stow jobs matching the specified criteria asynchronously, returning one page of results.
     *
     * @param request the list request with filters and pagination
     * @return a {@code CompletableFuture} that resolves to a page of stow jobs
     */
    CompletableFuture<Page<StowJob>> listAsync(StowJobListRequest request);

    /**
     * Lists all stow jobs matching the specified criteria by automatically iterating through pages.
     *
     * @param request the list request with filters
     * @return an {@code Iterable} over all matching stow jobs
     */
    Iterable<StowJob> listAll(StowJobListRequest request);

    /**
     * Creates a new stow job.
     *
     * @param request the creation request
     * @return the created stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob create(CreateStowJobRequest request);

    /**
     * Creates a new stow job asynchronously.
     *
     * @param request the creation request
     * @return a {@code CompletableFuture} that resolves to the created stow job
     */
    CompletableFuture<StowJob> createAsync(CreateStowJobRequest request);

    /**
     * Updates an existing stow job.
     *
     * @param stowJobId the ID of the stow job to update
     * @param request   the update request
     * @return the updated stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob update(StowJobId stowJobId, UpdateStowJobRequest request);

    /**
     * Updates an existing stow job asynchronously.
     *
     * @param stowJobId the ID of the stow job to update
     * @param request   the update request
     * @return a {@code CompletableFuture} that resolves to the updated stow job
     */
    CompletableFuture<StowJob> updateAsync(StowJobId stowJobId, UpdateStowJobRequest request);

    /**
     * Starts a stow job.
     *
     * @param stowJobId the ID of the stow job to start
     * @param version   the current optimistic lock version of the stow job
     * @return the updated stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob start(StowJobId stowJobId, int version);

    /**
     * Starts a stow job asynchronously.
     *
     * @param stowJobId the ID of the stow job to start
     * @param version   the current optimistic lock version of the stow job
     * @return a {@code CompletableFuture} that resolves to the updated stow job
     */
    CompletableFuture<StowJob> startAsync(StowJobId stowJobId, int version);

    /**
     * Pauses a stow job.
     *
     * @param stowJobId the ID of the stow job to pause
     * @param version   the current optimistic lock version of the stow job
     * @return the updated stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob pause(StowJobId stowJobId, int version);

    /**
     * Pauses a stow job asynchronously.
     *
     * @param stowJobId the ID of the stow job to pause
     * @param version   the current optimistic lock version of the stow job
     * @return a {@code CompletableFuture} that resolves to the updated stow job
     */
    CompletableFuture<StowJob> pauseAsync(StowJobId stowJobId, int version);

    /**
     * Cancels a stow job.
     *
     * @param stowJobId the ID of the stow job to cancel
     * @param version   the current optimistic lock version of the stow job
     * @return the updated stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob cancel(StowJobId stowJobId, int version);

    /**
     * Cancels a stow job asynchronously.
     *
     * @param stowJobId the ID of the stow job to cancel
     * @param version   the current optimistic lock version of the stow job
     * @return a {@code CompletableFuture} that resolves to the updated stow job
     */
    CompletableFuture<StowJob> cancelAsync(StowJobId stowJobId, int version);

    /**
     * Reopens a stow job.
     *
     * @param stowJobId the ID of the stow job to reopen
     * @param version   the current optimistic lock version of the stow job
     * @return the updated stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob reopen(StowJobId stowJobId, int version);

    /**
     * Reopens a stow job asynchronously.
     *
     * @param stowJobId the ID of the stow job to reopen
     * @param version   the current optimistic lock version of the stow job
     * @return a {@code CompletableFuture} that resolves to the updated stow job
     */
    CompletableFuture<StowJob> reopenAsync(StowJobId stowJobId, int version);

    /**
     * Closes a stow job.
     *
     * @param stowJobId the ID of the stow job to close
     * @param version   the current optimistic lock version of the stow job
     * @return the updated stow job
     * @throws de.joesst.dev.fulfillmenttools.FulfillmenttoolsException if the request fails
     */
    StowJob close(StowJobId stowJobId, int version);

    /**
     * Closes a stow job asynchronously.
     *
     * @param stowJobId the ID of the stow job to close
     * @param version   the current optimistic lock version of the stow job
     * @return a {@code CompletableFuture} that resolves to the updated stow job
     */
    CompletableFuture<StowJob> closeAsync(StowJobId stowJobId, int version);
}
